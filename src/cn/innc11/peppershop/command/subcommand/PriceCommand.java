package cn.innc11.peppershop.command.subcommand;

import cn.innc11.peppershop.PepperShop;
import cn.innc11.peppershop.command.PluginCommand;
import cn.innc11.peppershop.command.SubCommand;
import cn.innc11.peppershop.shop.Shop;
import cn.innc11.peppershop.utils.Pair;
import cn.innc11.peppershop.utils.Quick;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;

import static cn.innc11.peppershop.localization.LangNodes.*;
import static cn.innc11.peppershop.localization.LangNodes.im_intercept_console;

public class PriceCommand implements SubCommand
{
	@Override
	public boolean onExecute(CommandSender sender, PluginCommand pluginCommand, String masterCommand, String subCommand, String[] subArgs)
	{
		if (sender instanceof Player)
		{
			if (subArgs.length == 1) {
				if (PepperShop.isInteger(subArgs[0]))
				{
					Pair<Boolean, Shop> vi = PepperShop.ins.shopInteractionListener.isValidInteraction(sender.getName());

					if(vi!=null)
					{
						Shop shop = vi.value;

						if(shop.shopData.owner.equals(sender.getName()))
						{
							shop.shopData.price = Float.valueOf(subArgs[0]);

							PepperShop.ins.shopsConfig.getShopsConfig(shop, false).save();

							shop.updateSignText();

							sender.sendMessage(Quick.t(im_shop_price_updated,
									"PRICE", String.format("%.2f", shop.shopData.price)));
						} else {
							sender.sendMessage(Quick.t(im_not_allow_modify_price_not_owner));
						}
					} else {
						sender.sendMessage(Quick.t(im_not_selected_shop));
					}
				} else {
					sender.sendMessage(Quick.t(im_price_wrong_format));
				}
			} else {
				sender.sendMessage(Quick.t(im_price_wrong_args));
			}
		} else {
			sender.sendMessage(Quick.t(im_intercept_console));
		}

		return true;
	}

	@Override
	public String getSubCommandName()
	{
		return "price";
	}

	@Override
	public String[] getAliases()
	{
		return new String[]{"p"};
	}

	@Override
	public CommandParameter[] getSubParameters()
	{
		return new CommandParameter[]{new CommandParameter("value", CommandParamType.FLOAT, false)};
	}
}
