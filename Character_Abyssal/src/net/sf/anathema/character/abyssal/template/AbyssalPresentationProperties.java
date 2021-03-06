package net.sf.anathema.character.abyssal.template;

import net.sf.anathema.character.generic.impl.template.presentation.AbstractPresentationProperties;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.platform.svgtree.document.visualizer.ITreePresentationProperties;

public class AbyssalPresentationProperties extends AbstractPresentationProperties {

  private final String newActionResoure;
  private final AbyssalCharmPresentationProperties abyssalCharmPresentationProperties = new AbyssalCharmPresentationProperties();

  public AbyssalPresentationProperties(String newActionResoure) {
    super(new TemplateType(CharacterType.ABYSSAL));
    this.newActionResoure = newActionResoure;
  }

  @Override
  public String getNewActionResource() {
    return newActionResoure;
  }

  @Override
  public ITreePresentationProperties getCharmPresentationProperties() {
    return abyssalCharmPresentationProperties;
  }
}