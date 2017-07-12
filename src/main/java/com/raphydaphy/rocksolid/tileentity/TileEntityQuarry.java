package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.IHasInventory;
import com.raphydaphy.rocksolid.api.TileEntityPowered;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityQuarry extends TileEntityPowered implements IHasInventory
{

	public static final int OUTPUT = 0;
    public final ContainerInventory inventory;
    
    private int curX;
    private int curY;
    private int mineTick;
    
    protected int powerStored = 0;
    
    public TileEntityQuarry(final IWorld world, final int x, final int y) 
    {
        super(world, x, y, 20000, 100);
        this.inventory = new ContainerInventory(this, 1);
        
        this.curX = x - 9;
        this.curY = y - 2;
    }
    
    @Override
    protected boolean needsSync() 
    {
        return super.needsSync();
    }
    
    @Override
    protected void onSync() 
    {
        super.onSync();
    }
    
    public boolean isActive()
    {
    	return this.powerStored >= super.getPowerPerOperation();
    }
    
    @Override
    public boolean tryTickAction() 
    {
    	boolean ableToDig = false;
	    final ItemInstance output = this.inventory.get(0);
	    if (curX > this.x + 11)
		{
			curX = this.x - 9;
			curY--;
		}
	    
	    
	    while (world.getTile(curX, curY) == GameContent.TILE_AIR)
	    {
	    	curX++;
	    	if (curX > this.x + 11)
	    	{
	    		curX = this.x - 9;
	    		curY--;
	    	}
	    }
	    if ((output == null || output.getItem().equals(world.getTile(curX, curY).getDrops(world, curX, curY, TileLayer.MAIN, null).get(0).getItem()))&& world.isPosLoaded(curX, curY)) 
	    {
	    	if (this.getCurrentEnergy() >= super.getPowerPerOperation())
	    	{
	    		System.out.println(mineTick);
	    		ableToDig = true;
	    		if (mineTick == 10)
	    		{
	    			if (world.getTile(curX, curY).canBreak(world, curX, curY, TileLayer.MAIN) &&
	    				world.getTileEntity(curX, curY) == null)
	    			{
	    				List<ItemInstance> curTile = world.getTile(curX, curY).getDrops(world, curX, curY, TileLayer.MAIN, null);
		    			if (output == null)
		    			{
		    				this.inventory.set(0, new ItemInstance(curTile.get(0).getItem(), 1));
		    			}
		    			else if (output.getItem().equals(curTile.get(0).getItem()))
		    			{
		    				this.inventory.get(0).addAmount(1);
		    			}
		    			world.setTile(curX, curY, GameContent.TILE_AIR);
	    			}
	    			curX++;
		    		mineTick = 0;
	    		}
	    		else
	    		{
	    			mineTick++;
	    		}
	    		return ableToDig;
	    	}
	    }
        return ableToDig;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("powerStored", this.powerStored);
        set.addInt("curX", this.curX);
        set.addInt("curY", this.curY);
        set.addInt("mineTick", this.mineTick);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.powerStored = set.getInt("powerStored");
        this.curX = set.getInt("curX");
        this.curY = set.getInt("curY");
        this.mineTick = set.getInt("mineTick");
    }
    

	@Override
	public ContainerInventory getInventory() 
	{
		return this.inventory;
	}

	@Override
	public List<Integer> getInputs() 
	{
		return new ArrayList<Integer>();
	}

	@Override
	public List<Integer> getOutputs() 
	{
		List<Integer> outputSlots = new ArrayList<Integer>();
		outputSlots.add(0);
		return outputSlots;
	}

	@Override
	protected void setPower(int power) 
	{
		this.powerStored = power;
	}

	@Override
	protected int getPower() 
	{
		return this.powerStored;
	}

	@Override
	protected void onActiveChange(boolean active) {
		this.world.causeLightUpdate(this.x, this.y);
	}

}
