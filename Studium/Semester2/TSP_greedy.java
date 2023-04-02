package algo.�bungen3;

public class TSP_greedy {

	static int[][] Distanzen = new int[][] {{0,570,532,637,369,739,651,446,123,240,721,466,330,740,475,354,345,307,556,145,60,573,625,496,284,650,206,715,472},
		{570,0,239,593,715,574,472,422,601,365,649,340,335,117,720,600,221,432,820,430,538,431,184,530,297,61,620,729,180},
		{532,239,0,352,572,339,237,187,494,264,414,457,187,334,596,460,337,278,693,345,431,198,372,295,329,244,520,494,74},
		{637,593,352,0,375,125,214,288,480,564,91,800,474,686,279,258,670,367,343,610,553,184,739,131,657,596,450,130,426},
		{369,715,572,375,0,496,478,351,249,450,467,722,388,856,110,118,595,288,205,415,315,367,780,251,541,766,170,353,590},
		{739,574,339,125,496,0,138,320,608,585,119,800,490,686,430,378,670,502,511,633,683,244,739,233,660,596,579,304,426},
		{651,472,237,214,478,138,0,220,581,485,177,700,390,586,492,385,570,402,573,533,583,140,639,228,560,496,597,366,325},
		{446,422,187,288,351,320,220,0,367,268,366,533,180,515,376,289,403,135,459,318,373,170,567,209,339,425,384,438,264},
		{123,601,494,480,249,608,581,367,0,256,600,524,297,736,350,258,397,188,454,160,75,475,655,384,327,646,87,599,465},
		{240,365,264,564,450,585,485,268,256,0,661,262,95,502,509,362,135,190,599,125,185,407,430,445,106,412,280,704,235},
		{721,649,414,91,467,119,177,366,600,661,0,873,547,759,382,331,743,440,446,683,626,211,812,202,730,669,523,279,499},
		{466,340,457,800,722,800,700,533,524,262,873,0,357,490,759,624,130,457,852,333,435,642,175,700,195,400,548,942,378},
		{330,335,187,474,388,490,390,180,297,95,547,357,0,477,410,272,238,105,504,227,277,311,390,346,166,382,304,605,202},
		{740,117,334,686,856,686,586,515,735,502,759,490,477,0,869,742,360,575,960,580,675,513,155,615,444,89,763,813,267},
		{475,720,596,279,110,430,492,376,350,509,382,759,410,869,0,154,640,312,85,520,381,387,806,271,580,772,260,235,616},
		{354,600,460,258,118,378,385,289,258,362,331,624,272,742,154,0,500,238,238,410,295,252,656,136,433,647,190,395,476},
		{345,221,337,670,595,670,570,403,397,135,743,130,238,360,640,500,0,330,731,206,310,515,273,576,58,270,421,815,253},
		{307,432,278,367,288,502,402,135,188,190,440,457,105,575,312,238,330,0,405,243,243,278,490,247,265,482,200,508,309},
		{556,820,693,343,205,511,573,459,454,599,446,852,504,960,85,238,731,405,0,600,493,485,895,369,671,882,348,280,702},
		{145,430,345,610,415,633,533,318,160,125,683,333,227,580,520,410,206,243,600,0,110,454,501,496,145,521,243,760,350},
		{60,538,431,553,315,683,583,373,75,185,626,435,277,675,381,295,310,243,493,110,0,488,604,417,247,578,144,646,415},
		{573,431,198,184,367,244,140,170,475,407,211,642,311,513,387,252,515,278,485,454,488,0,564,108,497,436,450,322,266},
		{625,184,372,739,780,739,639,567,655,430,812,175,390,155,806,656,273,490,895,501,604,564,0,727,363,194,683,879,301},
		{496,530,295,131,251,233,228,209,384,445,202,700,346,615,271,136,576,247,369,496,417,108,727,0,505,522,342,269,360},
		{284,297,329,657,541,660,560,339,327,106,730,195,166,444,580,433,58,265,671,145,247,497,363,505,0,347,350,761,248},
		{650,81,244,596,766,596,496,425,646,412,669,400,382,89,772,647,270,482,882,521,578,436,194,522,347,0,668,731,162},
		{206,620,520,450,170,579,597,384,87,280,523,548,304,763,260,190,421,200,348,243,144,450,683,342,350,668,0,529,490},
		{715,729,494,130,353,304,366,438,599,704,279,942,605,813,235,395,815,508,280,760,646,322,879,269,761,731,529,0,567},
		{472,180,74,426,590,426,325,264,465,235,499,378,202,267,616,476,253,309,702,350,415,266,301,360,248,162,490,567,0}};
	
	
	
	public static void greedyRoute(int anzahlSt�dte) {
		boolean[] besucht = new boolean[20]; 
		int minDistanz = 0;
		int distanz = 0;
		int Stadt = 0;
		
		besucht[0] = true;
		int[] route = new int[20];
		route[0] = 0;
		
		
		for(int i = 1; i < anzahlSt�dte; i++) {
			minDistanz = 1000;
			for(int j = 0; j < anzahlSt�dte; j++) {
				if(Distanzen[route[i-1]][j] < minDistanz && besucht[j] == false) {
					Stadt = j;
					minDistanz = Distanzen[route[i-1]][j];
				}
			}
			besucht[Stadt] = true;
			distanz += Distanzen[route[i-1]][Stadt];
			route[i] = Stadt;
		}
		distanz += Distanzen[Stadt][0];
		
		
		
		for(int x = 0; x < anzahlSt�dte;x++) {
			System.out.print(route[x]);
		}
		System.out.println(" Distanz = " + distanz);
	}
	
	public static void main(String[] args){
		
		greedyRoute(8);
	
	
	}
}