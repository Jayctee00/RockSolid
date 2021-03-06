package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.IFluidProducer;
import com.raphydaphy.rocksolid.init.ModFluids;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityTank extends TileEntity implements IFluidAcceptor, IFluidProducer
{

    protected int fluidStored;
    protected int maxFluid;
    protected String fluidType;
    
    public TileEntityTank(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        maxFluid = 100000;
        
        if (fluidType == null)
        {
	    	if (Util.RANDOM.nextBoolean() == true)
	        {
	        	addFluid(10000, ModFluids.fluidWater.toString());
	        }
	    	else
	    	{
	    		fluidType = ModFluids.fluidEmpty.toString();
	    	}
        }
        
        sync();
    }
    
    private void sync()
    {
    	this.sendToClients();
    	this.onSync();
    }
    
    public float getTankFullnesss()
    {
    	if (fluidStored == 0)
    	{
    		return 0;
    	}
        return (float)this.fluidStored/(float)this.maxFluid;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        set.addInt("fluidStored", this.fluidStored);
        set.addInt("maxFluid", this.maxFluid);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        this.fluidStored = set.getInt("fluidStored");
        this.maxFluid = set.getInt("maxFluid");
    }

	@Override
	public int getCurrentFluid() 
	{
		return this.fluidStored;
	}

	@Override
	public int getMaxFluid() 
	{
		return this.maxFluid;
	}

	@Override
	public String getFluidType() 
	{
		return this.fluidType;
	}

	@Override
	public boolean removeFluid(int amount) 
	{
		if (this.fluidStored >= amount)
		{
			this.fluidStored -= amount;
			
			if (this.fluidStored == 0)
			{
				this.fluidType = ModFluids.fluidEmpty.toString();
			}
			this.sync();
			return true;
		}
		return false;
	}

	@Override
	public boolean addFluid(int amount, String type) 
	{
		if (this.fluidType == null || type.equals(this.fluidType) || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
		{
			if (this.fluidStored + amount <= this.maxFluid)
			{
				if (this.fluidType == null || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
				{
					this.fluidType = type;
				}
				this.fluidStored += amount;
				this.sync();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type) 
	{
		if (this.fluidType == ModFluids.fluidEmpty.toString() || this.fluidStored == 0)
		{
			this.fluidType = type;
			this.sync();
			return true;
		}
		return false;
	}

}
