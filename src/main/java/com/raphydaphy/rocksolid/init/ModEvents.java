package com.raphydaphy.rocksolid.init;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

import com.raphydaphy.rocksolid.gui.slot.PlayerInvSlot;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.Font;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.ContainerOpenEvent;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;
import de.ellpeck.rockbottom.api.event.impl.WorldRenderEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModEvents {
	public static void init(IEventHandler e)
	{
		e.registerListener(EntityTickEvent.class, (result, event) -> {
			if (event.entity instanceof AbstractEntityPlayer)
			{
				boolean engineActive = false;
				boolean hoverActive = false;
				int jetpackEnergy = 0;
				DataSet data;
				DataSet jetpackData = null;
				ItemInstance jetpack = null;
				
				AbstractEntityPlayer player = (AbstractEntityPlayer)event.entity;
				Input input = RockBottomAPI.getGame().getInput();
				
				
                if (player.getAdditionalData() != null) 	
                {
                	data = player.getAdditionalData();
                	engineActive = data.getBoolean("engineActive");
                	hoverActive = data.getBoolean("hoverActive");
                	if (data.getDataSet("jetpackData") != null)
                	{
                		if (data.getDataSet("jetpackData").getString("item_name") != null)
                		{
	                		if (data.getDataSet("jetpackData").getString("item_name").equals("") == false)
	                		{
			                	jetpack = ItemInstance.load(data.getDataSet("jetpackData"));
			                	if (jetpack != null)
			                	{
			                		jetpackData = jetpack.getAdditionalData();
				                	if (jetpackData != null)
				                	{
				                		jetpackEnergy = jetpackData.getInt("itemPowerStored");
				                	}
			                	}
	                		}
                		}
                	}
                }
                else
                {
                	data = new DataSet();
                	player.setAdditionalData(data);
                }
				
				if (jetpack != null)
				{
					if (input.isKeyDown(Keyboard.KEY_SPACE))
					{
						if (engineActive && jetpackEnergy > 3)
						{
							jetpackData.addInt("itemPowerStored", jetpackEnergy - 3);
							player.motionY += 0.05;
							player.fallAmount = 0;
						}
					}
					
					if (hoverActive && engineActive && jetpackEnergy > 4)
					{
						if (!input.isKeyDown(Keyboard.KEY_SPACE))
						{
							if (player.motionY < 0)
							{
								player.motionY = -0.0005f;
								jetpackData.addInt("itemPowerStored", jetpackEnergy - 4);
							}
						}
						player.fallAmount = 0;
					}
					
					
					if (engineActive)
					{
		                if (input.isKeyPressed(Keyboard.KEY_R))
		                {
		                	data.addBoolean("hoverActive", !hoverActive);
		                }
					}
	                if (input.isKeyPressed(Keyboard.KEY_Q))
	                {
	                	data.addBoolean("engineActive", !engineActive);
	                }
	                
	                if (jetpack != null)
	                {
		                jetpack.setAdditionalData(jetpackData);
		                DataSet jetpackSlotData = new DataSet();
		                jetpack.save(jetpackSlotData);
		                data.addDataSet("jetpackData", jetpackSlotData);
	                }
	                
					player.setAdditionalData(data);
					
				}
				
			}
			return EventResult.DEFAULT;
		});
		
		e.registerListener(WorldRenderEvent.class, (result, event) -> {
            if (event.world != null && RockBottomAPI.getNet().isThePlayer(event.player)) {
                DataSet data = event.player.getAdditionalData();
                if (data != null && !data.getBoolean("is_creative")) 
                {
                	int jetpackEnergy = 0;
                	if (data.getDataSet("jetpackData") != null)
                	{
                		if (data.getDataSet("jetpackData").getString("item_name") != null)
                		{
	                		if (data.getDataSet("jetpackData").getString("item_name").equals("") == false)
	                		{
			                	ItemInstance jetpack = ItemInstance.load(data.getDataSet("jetpackData"));
			                	if (jetpack != null)
			                	{
			                		DataSet jetpackData = jetpack.getAdditionalData();
				                	if (jetpackData != null)
				                	{
				                		jetpackEnergy = jetpackData.getInt("itemPowerStored");
				                	}
			                	}
	                		}
                		}
                	}
					if (jetpackEnergy > 0)
					{
						jetpackEnergy = jetpackEnergy / 10000;
					}
                	String jetpackEnergyString = FormattingCode.RED.toString() + "Depleted";
                	if (jetpackEnergy >= 70)
                	{
                		jetpackEnergyString = FormattingCode.GREEN.toString() +  jetpackEnergy + " Percent";
                	}
                	else if (jetpackEnergy >=20)
                	{
                		jetpackEnergyString = FormattingCode.YELLOW.toString() +  jetpackEnergy + " Percent";
                	}
                	else if (jetpackEnergy > 0)
                	{
                		jetpackEnergyString = FormattingCode.RED.toString() +  jetpackEnergy + " Percent";
                	}
                    Font font = event.assetManager.getFont();
                    
                    font.drawString(0.2F, 0.2F, FormattingCode.WHITE.toString() + "Jetpack fuel: " +  jetpackEnergyString, 0.0175F);
                    
                    String engine = FormattingCode.RED.toString() + "Engine";
                    String hover = FormattingCode.RED.toString() + "Hover";
                    
                    
                    if (data.getBoolean("engineActive"))
                    {
                    	engine = FormattingCode.GREEN.toString() + "Engine";
                    	
                    	if (data.getBoolean("hoverActive"))	
                    	{
                        	hover = FormattingCode.GREEN.toString() + "Hover";
                    	}
                    }
                    
                    	
            		font.drawString(0.2F, 0.7F, String.format(engine + FormattingCode.WHITE.toString() + " - " + hover), 0.0175F);
                }
            }
            return EventResult.DEFAULT;
        });
		
		e.registerListener(ContainerOpenEvent.class, (result, event) -> {
            
            
        	if (event.container != null && event.container.getClass().getName().equals("de.ellpeck.rockbottom.gui.container.ContainerInventory"))
        	{
        		AbstractEntityPlayer player = event.player;
        		if (player != null)
                {
            		if (player.getAdditionalData() != null)
            		{
            			if (!player.getAdditionalData().getBoolean("is_creative"))
    					{
            				event.container.addSlot(new PlayerInvSlot(player, "jetpackData",instance -> instance.getItem().equals(ModItems.jetpack), 165, 25));
            				event.container.addSlot(new PlayerInvSlot(player, "accessory2", null, 165, 45));
            				event.container.addSlot(new PlayerInvSlot(player, "accessory3", null, 165, 65));
    					}
            			
            		}
            	}
			}
			 
            return EventResult.MODIFIED;
		});
       	 
	}
}
