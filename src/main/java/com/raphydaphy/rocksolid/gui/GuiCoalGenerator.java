package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.tileentity.TileEntityCoalGenerator;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiCoalGenerator extends GuiContainer
{

    private final TileEntityCoalGenerator tile;
    
	public GuiCoalGenerator(final AbstractEntityPlayer player, final TileEntityCoalGenerator tile) {
	    super(player, 198, 150);
	    this.tile = tile;
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10, new Color(148,0,211), false, this.tile::getGeneratorFullness));
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 74, this.guiTop + 30, 8, 18, GuiCoalGenerator.FIRE_COLOR, true, this.tile::getFuelPercentage));
	}

}
