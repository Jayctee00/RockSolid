package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.Fluid;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.tex.Texture;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class FluidRenderer<T extends Fluid> extends DefaultTileRenderer<Fluid>
{
	public FluidRenderer(IResourceName texture) 
	{
		super(texture);
	}
		
	@Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, Fluid tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{
		
		int blockMeta = world.getState(x, y).get(Fluid.fluidLevel);
		
        if (blockMeta > 0)
        {
    		Texture curTex = manager.getTexture(super.texture).getSubTexture(0, 0, 12,  blockMeta);
    		curTex.drawWithLight(renderX, renderY + (((scale / 12) * (13 - blockMeta)) - (scale /12)), scale, (scale / 12) * (blockMeta), light);
        }
    }

}
