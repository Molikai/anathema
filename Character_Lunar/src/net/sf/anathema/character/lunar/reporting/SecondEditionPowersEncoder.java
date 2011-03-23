package net.sf.anathema.character.lunar.reporting;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.impl.CharacterUtilties;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.reporting.sheet.common.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.sheet.common.IPdfContentEncoder;
import net.sf.anathema.character.reporting.sheet.pageformat.IVoidStateFormatConstants;
import net.sf.anathema.character.reporting.sheet.util.LabelledValueEncoder;
import net.sf.anathema.character.reporting.sheet.util.PdfTextEncodingUtilities;
import net.sf.anathema.character.reporting.sheet.util.TableEncodingUtilities;
import net.sf.anathema.character.reporting.util.Bounds;
import net.sf.anathema.character.reporting.util.Position;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.util.Identificate;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

public class SecondEditionPowersEncoder implements IPdfContentBoxEncoder
{
  private Font font;
  private int lineHeight = IVoidStateFormatConstants.LINE_HEIGHT - 2;
  private final String powerBase = "Sheet.Lunar.Powers.";
  private final IResources resources;
  private final BaseFont baseFont;
  private final boolean isHorizontal;
  
  private static final TemplateType castelessType = new TemplateType(CharacterType.LUNAR, new Identificate(
  "Casteless")); //$NON-NLS-1$

  public SecondEditionPowersEncoder(IResources resources, BaseFont baseFont, boolean isHorizontal) {
    this.resources = resources;
    this.baseFont = baseFont;
    this.isHorizontal = isHorizontal;
  }

  public void encode(PdfContentByte directContent, IGenericCharacter character, Bounds bounds)
  {
	  int offsetX = 0, offsetY = isHorizontal ? 0 : 5;
	  font = TableEncodingUtilities.createFont(baseFont);
	  
	  if (isHorizontal)
	  {
		  bounds = new Bounds(bounds.x, bounds.y, bounds.width / 2, bounds.height);
		  font.setSize(IVoidStateFormatConstants.COMMENT_FONT_SIZE);
		  lineHeight -= 2;
	  }
	  
	  try
	  {
		  offsetY += writePowerNotes(directContent, "Shapeshifting", bounds, offsetX, offsetY);
		  if (!character.getTemplate().getTemplateType().equals(castelessType))
			  offsetY += writePowerNotes(directContent, "Tattoos", bounds, offsetX, offsetY);

		  if (isHorizontal)
		  {
			  bounds = new Bounds(
					  bounds.x + bounds.width,
					  bounds.y,
					  bounds.width,
					  bounds.height);
					  offsetY = 0;
		  }
		  
		  offsetY += writePowerNotes(directContent, "Tell", bounds, offsetX, offsetY);
	  }
	  catch (DocumentException e) { }
  }
  
  private int writePowerNotes(PdfContentByte directContent,
		  String power,
		  Bounds bounds,
		  int offsetX,
		  int offsetY) throws DocumentException
  {
	  Font boldFont = new Font(font);
	  boldFont.setStyle(Font.BOLD);
	  String text = resources.getString(powerBase + power);
	  Phrase phrase = new Phrase(text, boldFont);
	  int index = 0;
	  int totalHeight = 0;
	  while (!text.startsWith("##"))
	  {
		  Bounds newBounds = new Bounds(
				  bounds.x + offsetX,
				  bounds.y,
				  bounds.width - offsetX,
				  bounds.height - offsetY - totalHeight);
		  totalHeight += PdfTextEncodingUtilities.encodeText(directContent, phrase,
				  newBounds, lineHeight).getLinesWritten() * lineHeight;
		  text = resources.getString(powerBase + power + (++index));
		  phrase = new Phrase(text, font);
	  }
	  if (!isHorizontal)
	  {
		  Bounds newBounds = new Bounds(
				  bounds.x + offsetX,
				  bounds.y + bounds.height - offsetY - totalHeight,
				  bounds.x - offsetX,
				  lineHeight);
		  totalHeight += PdfTextEncodingUtilities.encodeText(directContent, new Phrase(" ", font),
				  newBounds, lineHeight).getLinesWritten() * lineHeight;
	  }
	  return totalHeight;
  }

	@Override
	public String getHeaderKey() {
		return "Lunar.Powers";
	}
}