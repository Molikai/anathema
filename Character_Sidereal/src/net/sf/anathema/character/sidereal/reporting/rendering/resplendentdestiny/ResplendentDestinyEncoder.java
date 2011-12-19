package net.sf.anathema.character.sidereal.reporting.rendering.resplendentdestiny;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import net.sf.anathema.character.reporting.pdf.content.ReportContent;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.PdfLineEncodingUtilities;
import net.sf.anathema.character.reporting.pdf.rendering.general.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IBoxContentEncoder;
import net.sf.anathema.lib.resources.IResources;

public class ResplendentDestinyEncoder implements IBoxContentEncoder {

  private IResources resources;
  private float lineHeight;

  public ResplendentDestinyEncoder(int fontSize, IResources resources) {
    this.resources = resources;
    this.lineHeight = fontSize * 1.5f;
  }

  public void encode(SheetGraphics graphics, ReportContent reportContent, Bounds bounds) throws DocumentException {
    int yPosition = (int) (bounds.getMaxY() - lineHeight);
    graphics.drawLabelledContent(getLabel("Label.College"), null, new Position(bounds.x, yPosition), bounds.width); //$NON-NLS-1$
    yPosition -= lineHeight;
    graphics.drawLabelledContent(getLabel("Label.Name"), null, new Position(bounds.x, yPosition), bounds.width); //$NON-NLS-1$
    yPosition -= lineHeight;
    graphics.drawLabelledContent(getLabel("Label.EffectDice"), null, new Position(bounds.x, yPosition), 
      bounds.width); //$NON-NLS-1$
    yPosition -= lineHeight;
    graphics.drawLabelledContent(getLabel("Label.Duration"), null, new Position(bounds.x, yPosition), 
      bounds.width); //$NON-NLS-1$
    yPosition -= lineHeight;
    graphics.drawLabelledContent(getLabel("Label.Effects"), null, new Position(bounds.x, yPosition), bounds.width); //$NON-NLS-1$
    yPosition -= lineHeight;
    encodeLines(graphics.getDirectContent(), bounds, new Position(bounds.x, yPosition), 4);
    yPosition -= 4 * lineHeight;
    graphics.drawLabelledContent(getLabel("Label.Trappings"), null, new Position(bounds.x, yPosition), 
      bounds.width); //$NON-NLS-1$
    yPosition -= lineHeight;
    encodeLines(graphics.getDirectContent(), bounds, new Position(bounds.x, yPosition), 5);
  }

  private void encodeLines(PdfContentByte directContent, Bounds bounds, Position lineStartPosition, int count) {
    float minX = bounds.getMinX();
    float maxX = bounds.getMaxX();
    PdfLineEncodingUtilities.encodeHorizontalLines(directContent, lineStartPosition, minX, maxX, lineHeight, count);
  }

  public String getHeaderKey(ReportContent reportContent) {
    return "Sidereal.ResplendentDestiny"; //$NON-NLS-1$  
  }

  protected final String getLabel(String key) {
    return resources.getString("Sheet.ResplendentDestiny." + key) + ":"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  public boolean hasContent(ReportContent content) {
    return true;
  }
}
