package net.sf.anathema.character.reporting.pdf.layout.extended;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import net.sf.anathema.character.reporting.pdf.content.ReportContent;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.pdf.rendering.page.PdfPageConfiguration;
import net.sf.anathema.lib.resources.IResources;

public class ExtendedSecondPageEncoder extends AbstractPdfPageEncoder {

  public ExtendedSecondPageEncoder(IExtendedPartEncoder partEncoder, ExtendedEncodingRegistry registry, IResources resources, int essenceMax,
    PdfPageConfiguration pageConfiguration) {
    super(partEncoder, registry, resources, pageConfiguration);
  }

  public void encode(Document document, SheetGraphics graphics, ReportContent content) throws DocumentException {
    // Left column (top-down)
    float leftDistanceFromTop = 0;
    float healthHeight = encodeHealth(graphics, content, leftDistanceFromTop, 175);
    leftDistanceFromTop += calculateBoxIncrement(healthHeight);
    float movementHeight = encodeMovement(graphics, content, leftDistanceFromTop, 76);
    leftDistanceFromTop += calculateBoxIncrement(movementHeight);
    float socialCombatHeight = encodeSocialCombatStats(graphics, content, leftDistanceFromTop, 125);
    leftDistanceFromTop += calculateBoxIncrement(socialCombatHeight);

    // Right columns (top-down)
    float rightDistanceFromTop = 0;
    float weaponryHeight = encodeWeaponry(graphics, content, rightDistanceFromTop, 140);
    rightDistanceFromTop += calculateBoxIncrement(weaponryHeight);
    float armourHeight = encodeArmourAndSoak(graphics, content, rightDistanceFromTop, 111);
    rightDistanceFromTop += calculateBoxIncrement(armourHeight);
    float combatHeight = encodeCombatStats(graphics, content, rightDistanceFromTop, 125);
    rightDistanceFromTop += calculateBoxIncrement(combatHeight);

    // Fill in remaining space with inventory
    float distanceFromTop = Math.max(leftDistanceFromTop, rightDistanceFromTop);
    encodeInventory(graphics, content, distanceFromTop, getContentHeight() - distanceFromTop);

    encodeCopyright(graphics);
  }

  private float encodeInventory(SheetGraphics graphics, ReportContent content, float distanceFromTop, float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getRegistry().getPossessionsEncoder(), 1, 3, distanceFromTop, height);
  }

  private float encodeArmourAndSoak(SheetGraphics graphics, ReportContent content, float distanceFromTop, float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getRegistry().getArmourContentEncoder(), 2, 2, distanceFromTop, height);
  }

  private float encodeSocialCombatStats(SheetGraphics graphics, ReportContent content, float distanceFromTop,
    float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getPartEncoder().getSocialCombatEncoder(), 1, 1, distanceFromTop, height);
  }

  private float encodeCombatStats(SheetGraphics graphics, ReportContent content, float distanceFromTop, float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getPartEncoder().getCombatStatsEncoder(), 2, 2, distanceFromTop, height);
  }

  private float encodeHealth(SheetGraphics graphics, ReportContent content, float distanceFromTop, float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getPartEncoder().getHealthEncoder(), 1, 1, distanceFromTop, height);
  }

  private float encodeMovement(SheetGraphics graphics, ReportContent content, float distanceFromTop, float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getPartEncoder().getMovementEncoder(), 1, 1, distanceFromTop, height);
  }

  private float encodeWeaponry(SheetGraphics graphics, ReportContent content, float distanceFromTop, float height) throws DocumentException {
    return encodeFixedBox(graphics, content, getRegistry().getWeaponContentEncoder(), 2, 2, distanceFromTop, height);
  }
}