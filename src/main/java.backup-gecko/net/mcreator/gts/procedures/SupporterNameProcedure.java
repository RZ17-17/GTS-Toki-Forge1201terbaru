package net.mcreator.gts.procedures;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class SupporterNameProcedure {
	public static String execute() {
		double RNG = 0;
		RNG = Mth.nextInt(RandomSource.create(), 1, 6);
		if (RNG == 1) {
			return "Hane";
		}
		if (RNG == 2) {
			return "Ocean Men";
		}
		if (RNG == 3) {
			return "M3Donboi";
		}
		if (RNG == 4) {
			return "yeebeeseadog";
		}
		if (RNG == 5) {
			return "\u51B7\u996E";
		}
		if (RNG == 6) {
			return "Qerkn";
		}
		return "";
	}
}
