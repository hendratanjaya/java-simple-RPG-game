package attributes;
import entities.Player;

import java.util.Random;
import java.util.Arrays;
public class Map {
	
	private final int mapWidth = 335;
	private final int mapHeight = 315;
	
	//map dilebihkan ukurannya untuk menyesuaikan tampilan pada display map nanti
	//map yang dapat diakses player mulai dari x = 17 sampai x = 316 dan y = 7 sampai y = 306
	
	private Random rand = new Random();
	public String[] makeMap () {
		
		String[] map = new String[mapHeight];
		
		int idx = 0;
//		String[] temp = new String[3];
		for(int i= 0; i < mapHeight; i++) {
			
			idx++;
			
			if(idx == 3) {
				idx = 0;
				int start = i - 2;
				int end = start + 2;
				editString(map,start,end);
			
			}
			
		}
		generateCoin(map);
		
		return map;
	}
	
	public String[] displayMap(String[] map, Player p) {
		
		
		int width = 37;
		int length = 17;
		String[] toDisplay = new String[length];
		char[][] temp = new char[length][width];
		int endY = (p.getYpos()+7) >= 313? 313 : p.getYpos()+7;
		int beginY,beginX;
		if(endY == 313) {
			beginY = endY-14;
		}
		else beginY = (p.getYpos()-7) <= 0 ? 0 : p.getYpos()-7;
		
		int endX = (p.getXpos()+17) >= 333? 333 : p.getXpos()+17;
		if(endX == 333) {
			beginX = endX-34;
		}
		else beginX = (p.getXpos()-17) <= 0 ? 0 : p.getXpos()-17;
		
		
		
		
		
		for(int i = 0; i < length ; i++) {
			
			for (int j = 0;j < width; j++){
				if(i== 0 || i == length -1) {
					temp[i][j] = '#';
				}else if(j == 0 || j == width-1) {
					temp[i][j] = '#';
				}else {
					
//					temp[j] = map[beginY+i].charAt(beginX+j);
					temp[i][j] = ' ';
				}
			}
			if(i==0 || i == length-1) {
				toDisplay[i] = new String(temp[i]);
			}
		}
		int i = 1;
		int j = 1;
		for(int y = beginY; y <= endY; y++) {
			j = 1;
			for(int x = beginX; x <= endX; x++) {
				if(y == p.getYpos() && x==p.getXpos()) {
					temp[i][j++] = 'X';
				}
				else temp[i][j++] = map[y].charAt(x);
			}
			toDisplay[i] = new String(temp[i]);
			i++;
			
		}
		
//		System.out.println(beginY + " " + endY);
//		System.out.println(beginX + " " + endX);
//		System.out.println(count);
		
		return toDisplay;
	}
	private int[] oldPattern;
	private void editString(String[] map, int start,int end) {
		
		
		
		int[] pattern = makePattern();
		
		for(int i = start; i <= end; i++) {
			char[] ch = new char[mapWidth];
			randomizeTexture(ch,pattern);
			map[i] = new String(ch);
		}
		
		
	}
	
	private void randomizeTexture(char[] ch, int [] pattern) {
		char[] grass = {'V',' ','v'};
		char[] rock = {'#',' '};
		for(int i = 0; i < mapWidth;i++) {
			if(pattern[i] == 1) {
				ch[i] = grass[rand.nextInt(grass.length)];
						
			}else if(pattern[i] == 2){
				ch[i] = rock[rand.nextInt(rock.length)];
				
			}else if(pattern[i] >= 3) {
				ch[i] = ' ';
			}
			//System.out.println(pattern);
		}
		
	}
	private int[] makePattern() {
		int[] pattern = new int[mapWidth];
		int count = 0;
		
//		int grass = 1; 
//		int rock = 2;
//		int empty = 3;
		
		int texture = 0;
		
		texture = rand.nextInt(7)+1;
		int prev;
		if(oldPattern != null) {
			do {
				prev = rand.nextInt(7) +1;
			}while(prev + oldPattern[0] == 3);
		}else {
			prev = texture;
		}
		for(int i = 0; i < mapWidth; i++) {
			
			if(count == 3) {
				prev = texture;
				count = 0;
				if(oldPattern== null) {
					do {
						texture = rand.nextInt(7)+1;
					}while(prev + texture == 3 || (prev == 2 && texture ==2) || (prev+texture == 2));
					
				}else {
					do {
						texture = rand.nextInt(7)+1;
						
					}while(prev + texture == 3 || oldPattern[i] + texture == 3 || (prev == 2 && texture ==2) || (prev +texture == 2));
				}
				
			}
			pattern[i] = texture;
			count++;
			
			
		}
		
		
		
		oldPattern = Arrays.copyOf(pattern, pattern.length);
		
		return pattern;
	}
	
	private void generateCoin(String[] map) {
		
		int x = 0;
		int y = 0;
		for(int i = 0; i < 200; i++) {
			do {
				x = rand.nextInt(300)+17;
				y = rand.nextInt(300)+7;
			}while((isRock(map,x,y)));
			char[] temp = map[y].toCharArray();
			temp[x] = '0';
			map[y] = new String(temp);
		}
		
		
	}
	private boolean isRock(String[] map, int x, int y) {
		
		if(map[y].charAt(x) == '#' || map[y].charAt(x) == '0') {
			return true;
		}else if(map[y].charAt(x+1) == '#' &&
				 map[y].charAt(x-1) == '#' &&
				 map[y-1].charAt(x) == '#' &&
				 map[y+1].charAt(x) == '#') {
			
			return true;
		}
		
		
		
		return false;
	}
	
}
