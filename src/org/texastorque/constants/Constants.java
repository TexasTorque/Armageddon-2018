package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

	public static final Constant IN_LIMIT = new Constant("IN_LIMIT", .75);
	// HumanInput
	public static double HI_DBDBT = 1d;
	public static final Constant HI_DBDT = new Constant("HI_DEADBANDDT", 0.2);
	public static final Constant HI_DOBOTHSHOOTERS = new Constant("HI_DOBOTHSHOOTERS", 1.0);

	// Drivebase
	public static final Constant DB_MVELOCITY = new Constant("DB_MVELOCITY", 168.0);
	public static final Constant DB_MACCELERATION = new Constant("DB_MACCELERATION", 60.0);
	public static final Constant DB_TURN_MVELOCITY = new Constant("DB_TURN_MVELOCITY", 30.0); // 60
	public static final Constant DB_TURN_MACCELERATION = new Constant("DB_TURN_MACCELERATION", 8.0); // 10

	public static final Constant DB_RIGHT_PV_P = new Constant("DB_RIGHT_PV_P", .08);
	public static final Constant DB_RIGHT_PV_V = new Constant("DB_RIGHT_PV_V", .008);
	public static final Constant DB_RIGHT_PV_ffV = new Constant("DB_RIGHT_PV_ffV", 0.002);
	public static final Constant DB_RIGHT_PV_ffA = new Constant("DB_RIGHT_PV_ffA", 0.0);

	public static final Constant DB_LEFT_PV_P = new Constant("DB_LEFT_PV_P", .08);
	public static final Constant DB_LEFT_PV_V = new Constant("DB_LEFT_PV_V", .008);
	public static final Constant DB_LEFT_PV_ffV = new Constant("DB_LEFT_PV_ffV", 0.002);
	public static final Constant DB_LEFT_PV_ffA = new Constant("DB_LEFT_PV_ffA", 0.0);

	public static final Constant DB_TURN_PV_P = new Constant("DB_TURN_PV_P", .078);
	public static final Constant DB_TURN_PV_V = new Constant("DB_TURN_PV_V", .006);
	public static final Constant DB_TURN_PV_ffV = new Constant("DB_TURN_PV_ffV", .005);
	public static final Constant DB_TURN_PV_ffA = new Constant("DB_TURN_PV_ffA", 0.0);

	public static final Constant DB_RIMP_P = new Constant("DB_RIMP_P", .078);
	public static final Constant DB_RIMP_V = new Constant("DB_RIMP_V", 0);
	public static final Constant DB_RIMP_ffV = new Constant("DB_RIMP_ffV", 0);
	public static final Constant DB_RIMP_ffA = new Constant("DB_RIMP_ffA", 0);

	// Pivot
	public static final Constant PT_MVELOCITY = new Constant("PT_MVELOCITY", 400.0);
	public static final Constant PT_MACCELERATION = new Constant("PT_MACCELERATION", 70.0);
	public static final Constant PT_PV_P = new Constant("PT_PV_P", .08);
	public static final Constant PT_PV_V = new Constant("PT_PV_V", .008);
	public static final Constant PT_PV_ffV = new Constant("PT_PV_ffV", 0.002);
	public static final Constant PT_PV_ffA = new Constant("PT_PV_ffA", 0.0);

	public static final Constant TUNED_VOLTAGE = new Constant("TUNED_VOLTAGE", 12.5);
}
