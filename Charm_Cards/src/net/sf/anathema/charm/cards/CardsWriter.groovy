package net.sf.anathema.charm.cards

import groovy.xml.*
import net.sf.anathema.character.generic.impl.magic.charm.type.*
import net.sf.anathema.character.generic.magic.*
import net.sf.anathema.character.generic.magic.charms.type.*



class CardsWriter {

	Writer writer

	CardsWriter(){
		this(new StringWriter())
	}

	CardsWriter(Writer writer){
		this.writer = writer
	}

	def write(ICharm[] charms){
		def xml = new MarkupBuilder(writer)
		xml.cards() {
			charms.each {charm ->
				card() {
					name(charm.id)
					exalt(charm.characterType)
					ability(charm.primaryTraitType)
					stats() {
						type(buildTypeText(charm))
					}
				}
			}
		}
		writer.toString()
	}

	String buildTypeText(ICharm charm){
		def model = charm.charmTypeModel;
		def type = model.charmType
		StringBuilder builder = new StringBuilder(type.toString())
		if (model.hasSpecialsModel()){
			if (type == CharmType.Simple){
				SimpleSpecialsModel specialsModel = model.specialsModel
				builder.append(" (")
				def speed = specialsModel.speed
				def dv = specialsModel.defenseModifier
				if (speed != 6){
					builder.append("Speed ")
					builder.append(specialsModel.speed)
				}
				if (dv != -1){
					if (speed != 6){
						builder.append(", ")
					}
					builder.append("DV ")
					builder.append(dv)
				}
				builder.append(")")
			}
			if (type == CharmType.Reflexive){
				ReflexiveSpecialsModel specialsModel = model.specialsModel
				builder.append(" (Step ")
				builder.append(specialsModel.primaryStep)
				def secondaryStep = specialsModel.secondaryStep
				if (secondaryStep){
					builder.append(" or ")
					builder.append(secondaryStep)
				}
				builder.append(")")
			}
		}
		builder.toString()
	}
}