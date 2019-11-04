package aoRuns;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/* Программа для подсчета наиболее эффективного 
 * крафта рун с заданным количесвом пыли и крошки.
 * (prototype)
 */
class Calculation {
	// В массивах указано необходимое для крафта руны количество золотой пыли
	// и кристаллической крошки. 
	// Записаны все возможные вариации пропорций ингредиетов при крафте рун.
	private int[][] runesLevel = {
			{ 38400, 28, 19200, 31, 9600, 34, 4800, 38, 
			2400, 41, 1200, 45, 600, 50, 300, 55, 150, 61, 75, 67, 
			38, 73, 19, 81, 9, 89, 5, 97, 2, 107 },
			
			{ 57600, 43, 28800, 47, 14400, 52, 7200, 57,
			3600, 62, 1800, 69, 900, 75, 450, 83, 225, 91, 113, 100, 
			56, 110, 28, 122, 14, 134, 7, 147, 4, 162 },
			
			{ 86400, 64, 43200, 70, 21600, 77, 10800, 85, 
			5400, 93, 2700, 102, 1350, 113, 675, 124, 338, 136, 
			169, 150, 84, 165, 42, 182, 21, 200, 11, 220, 5, 242 },
			
			{ 129600, 95, 64800, 105, 32400, 115, 16200, 127, 
			8100, 140, 4050, 154, 2025, 169, 1013, 186, 506, 205, 
			253, 225, 127, 248, 63, 272, 32, 300, 16, 330, 8, 362 },
			
			{ 194400, 143, 97200, 157, 48600, 173, 24300, 190, 
			12150, 209, 6075, 230, 3038, 253, 1519, 278, 759, 306, 
			380, 339, 190, 370, 95, 407, 47, 448, 24, 492, 12, 542 },
			
			{ 291601, 215, 145800, 236, 72900, 260, 36450, 285, 
			18225, 314, 9113, 345, 4556, 380, 2278, 418, 1139, 460, 
			570, 506, 285, 556, 142, 612, 71, 673, 36, 741, 18, 815 }
	};
	
	// Содержит количество пыли и крошки, которые есть у игрока.
	private int thereIsGoldDust, thereIsCrystalBaby;
	private int countIter = 0; // Записывает количество скрафченных рун.
	//int checkoutCounter;
	
	// Списочный массив для записи ВСЕХ скрафченных рун
	// (Сюда записываются абсолютно все созданные руны ).
	private ArrayList <Integer> whatToCraft = new ArrayList <Integer>();
	// Для временной записи выше перечисленного.
	private ArrayList <Integer> allCraftRunes = new ArrayList <Integer>();
	
	// В списочный массив будут записыны скрафченные руны ПО ИТОГУ
	// (После создания руны использовавшиеся для крафта стираются ).
	private ArrayList <Integer> craftRunes = new ArrayList <Integer>();
	
	// Объявляем массив для записи скрафченных рун.
	int[] tmp = { 2, 0, 3, 0, 4, 0, 5, 2, 6, 0, 7, 0,
			8, 0, 9, 0, 10, 0, 11, 0, 12, 0 };
	
	// Конструктор класса Calculation
	Calculation(int g, int c){
		thereIsGoldDust = g;
		thereIsCrystalBaby = c;
	}

	
	// Записывает необходимое количество ингредиентов для крафта.
	int gold(int g, int l, int index, int count) {
		l = (l != 0 ? l / 2 : 0);
		//System.out.println((runesLevel[l][index]) * count +
				//" " + count + " gold");
		g += ((runesLevel[l][index]) * count);
		return g;
	}
	int crystal(int c, int l, int index, int count) {
		l = (l != 0 ? l / 2 : 0);
		c += ((runesLevel[l][index + 1]) * count);
		return c;
	}
	
	// Проверяет есть ли две руны для крафта руны более высокого уровня.
	// И если нет, то возвращает недостоющее количество рун, необходимое для крафта.
	int[] checkCountRunes(int[] tmp, int l, int count) {
		int[] tmpAccess = { 0 };
		// Если нужно скрафтить 2 руны, то возвращаем true, 
		// т.к. для их крафта руны ступенью ниже не нужны.
		if(l == 0) return tmpAccess; 
		// Если хватает рун для крафта, то возвращаем true.
		else if(tmp[l - 1] >= count * 2) return tmpAccess;
		/* Иначе узнаем какие руны нужны для крафт
		(Например, если хотим сделать руну 6 ступени, а есть только
		одна руна 5 ступени, то вычисляем каких рун не хватает для крафта
		руны 5 ступени. И так вплоть до рун 2 уровня).
		 */
		else {
			ArrayList <Integer> tmpResult = new ArrayList<Integer>();
			int n = count * 2 - (tmp[l - 1]);
			
			for(int i = l; i > 0; i -= 2) {
				
				tmpResult.add(i - 2);
				tmpResult.add(n);
				
				if((i - 2) == 0) break;
				
				if(tmp[i - 3] >= n * 2) break;
				else n = (n * 2) - (tmp[i - 3]);
			}
			
			int k = tmpResult.size();
			int[] tmpCountRunes = new int[k];

			for(int j = 0; j < tmpResult.size(); j += 2) {
				tmpCountRunes[j] = tmpResult.get(k - 2);
				tmpCountRunes[j + 1] = tmpResult.get(k - 1);
				
				k -= 2;
			}
				
			return tmpCountRunes;
		}
	}
	
	// Проверяет наличие пыли и крошки необходимое для крафта.
	boolean checkAvailabilityIngr(int g, int c, int l, int index, int count) {
		g = gold(g, l, index, count);
		c = crystal(c, l, index, count);
		//System.out.println(g + " " + c + " " 
		//+ thereIsGoldDust + " " + thereIsCrystalBaby + " checkAvailability");
		if(g <= thereIsGoldDust && c <= thereIsCrystalBaby)	{
			//System.out.print(" cAI true ");
			return true;
		}
		else {
			//System.out.print(" cAI false ");
			return false;
		}
	}
	
	// Если для крафта есть все необходимое, то создает руну.
	int[] checkAll (int[] tmp, int g, int c, int l, int count, int r, int[] index) {
		//countIter += 1;
		int[] gcr = { g, c, r, 0 };
		
		int[] tmpCheck = checkCountRunes(tmp, l, count);
		if(tmpCheck.length == 1) {
			
			int t = (l != 0 ? l / 2 : 0);
			
			//System.out.println("checkAvailability");
			if(checkAvailabilityIngr(gcr[0], gcr[1], l, index[t], count)) {
				//System.out.println("Craft");
				gcr[0] = gold(gcr[0], l, index[t], count);
				gcr[1] = crystal(gcr[1], l, index[t], count);
				gcr[2] = sumRuns(gcr[2], l, count);
				
				if(l != 0) tmp[l - 1] -= count * 2;
				tmp[l + 1] += count;
				
				allCraftRunes.add(tmp[l]);
				allCraftRunes.add(count);
				allCraftRunes.add((runesLevel[t][index[t]]) * count);
				allCraftRunes.add((runesLevel[t][index[t] + 1]) * count);
				
				return gcr;
			} 
			else {
				//System.out.println("Return false gcr");
				gcr[3] = 2;
				return gcr;
			}
		} else {
			gcr[3] = 1;
			return gcr;
			/*checkoutCounter += 1;
				
			// Если не хватает рун для крафта, то крафтим недостающее 
			// количество рун.
			//System.out.println(gcr[0] + " до вызова. " + checkoutCounter);
			int[] tmpResult = checkAll(tmp, gcr[0], gcr[1], l-2, tmpCount, 
					gcr[2], index);
			//System.out.println(gcr[0] + " после вызова. " + checkoutCounter);
			if(tmpResult[0] > 0) {
				if(checkoutCounter > 1) {
					checkoutCounter -= 1;
					return tmpResult;
				} else
					tmpResult = checkAll(tmp, tmpResult[0], tmpResult[1], l, 
							count, tmpResult[2], index);
				
				if(tmpResult[0] > 0) return tmpResult;
			}
			gcr[3] = 1;
			//System.out.println(gcr[0] + " return gcr");
			return gcr;*/
		}
	}
	
	// Вычисляет "сумму рун". 
	int sumRuns(int r, int l, int count) {
		if(l != 0) {
			r += ((l * 2) * 100) * count;
		} else r += 100 * count;
		//System.out.println(r + " - result");
		return r;
	}
	
	// Обнуляет массив tmp, в котором записаны скрафченные руны за итерацию.
	int[] dropTmp(int[] tmp) {
		//System.out.println(tmp[5] + " dropTmp");
		tmp = new int[]{ 2, 0, 3, 0, 4, 0, 5, 2, 6, 0, 7, 0,	
				8, 0, 9, 0, 10, 0, 11, 0, 12, 0 };
		return tmp;
	}
	
	// С помощью переменной result находим наиболее эффективный результат парсера.
	void maxCraftRunes(int[] tmp, int g, int c, int r) {
		if (craftRunes.size() == 0) {
			craftRunes.addAll(Arrays.asList(r, g, c));
			for(int i = 0; i < tmp.length; i++) 
				craftRunes.add(tmp[i]);
			
			whatToCraft.addAll(allCraftRunes);
		} else {
			//System.out.println("Result'ы: " + craftRunes.get(0) + " " + r);
			if((craftRunes.get(0)) < r) {
				craftRunes.clear();
				
				craftRunes.addAll(Arrays.asList(r, g, c));
				for(int i = 0; i < tmp.length; i++) 
					craftRunes.add(tmp[i]);
				
				whatToCraft.clear();
				whatToCraft.addAll(allCraftRunes);
			}
		}
		allCraftRunes.clear();
	}
	
	// Метод в котором производятся вызовы всех остальных методов.
	int[] controller(int[] tmp, int g, int c, int l, int count, int r, int[] index) {
		int[] tmpResult;
		tmpResult = checkAll(tmp, g, c, l, count, r, index);
		
		// Если рун для крафта не хватает, то крафтим недостающие.
		if(tmpResult[3] == 1) {
			int[] tmpCount = checkCountRunes(tmp, l, count);
			
			for(int k = 0; k < tmpCount.length; k += 2) {
				int tmpLevel = tmpCount[k];
				int tmpQuantity = tmpCount[k + 1];
				
				tmpResult = checkAll(tmp, g, c, tmpLevel, tmpQuantity, r, index);
				
				// Если ресурсов для крафта нехватило, то выход из цикла.
				if (tmpResult[3] == 2) break; 
				
				g = tmpResult[0];
				c = tmpResult[1];
				r = tmpResult[2];
			}
			if(tmpResult[3] == 0) {
				tmpResult = checkAll(tmp, g, c, l, count, r, index);
			}
			//tmpResult = checkAll(tmp, g, c, l-2, tmpCount, r, index);
		}
		//System.out.println(tmpResult[0] + " controller");
		
		return tmpResult;
	}
	
	// Ищем максимальное количество рун, которые можно скрафтить
	// с имеющимися ингредиентами.
	int[] parser() {
		for(int i = 0; i < runesLevel[0].length; i += 2) {
			countIter += 1;
			
			int[] index = { 30, 40, 50, 60, 70 };
			int goldDust, crystalBaby, result;
			goldDust = crystalBaby = result = 0;
			int count = 2;
			
			// Указывается индекс уровня рун в tmp 
			// (т.е. 2 ур. = 0, 3 ур = 2, 4 ур. = 4, ..., 7 ур. = 9).
			int level = 0; 
			
			index[0] = i;
			
			
			for(int b = 0; b < runesLevel[1].length; b += 2) {
				countIter += 1;
				
				level = 2;
				count = 1;
				index[1] = b;
		
				
				for(int d = 0; d < runesLevel[2].length; d += 2) {
					countIter += 1;
					
					level = 4;
					count = 1;
					index[2] = d;
								
					
					for(int y = 0; y < runesLevel[3].length; y += 2) {
						countIter += 1;
						
						level = 6;
						count = 1;
						index[3] = y;
						
						/*int[] resultMethod_3 = controller(tmp, goldDust, 
								crystalBaby, level, count, result, index);
						
						if(resultMethod_3[3] == 2) {
							maxCraftRunes(tmp, goldDust, crystalBaby, result);
							continue;
						}
						goldDust = resultMethod_3[0];
						crystalBaby = resultMethod_3[1];
						result = resultMethod_3[2];*/
						
						for(int u = 0; u < runesLevel[4].length; u += 2) {
							countIter += 1;
							
							level = 8;
							count = 1;
							index[4] = u;
							
							while (checkAvailabilityIngr(goldDust, crystalBaby, 
									level, index[4], count)) {
								
								int[] resultMethod_4 = controller(tmp, goldDust, 
										crystalBaby, level, count, result, index);
								
								if(resultMethod_4[3] == 2) break;
								goldDust = resultMethod_4[0];
								crystalBaby = resultMethod_4[1];
								result = resultMethod_4[2];
							}
							
							maxCraftRunes(tmp, goldDust, crystalBaby, result);
							goldDust = crystalBaby = result = 0;
							tmp = dropTmp(tmp);
						}
					}
				}
			}
		}
		
		// Записывает в файл все скрафченные руны.
		try(FileWriter writer = new FileWriter("C:\\Program Language\\Workspace\\myJava\\craftRunes.txt", false)) {
			for(int j = 0; j < whatToCraft.size(); j += 4) {
				String value = Integer.toString(whatToCraft.get(j));
				writer.append("Ур. рун - ");
				writer.append(value);
				value = Integer.toString(whatToCraft.get(j+1));
				writer.append(" Кол-во - ");
				writer.append(value);
				value = Integer.toString(whatToCraft.get(j+2));
				writer.append("  ||  Потребовалось пыли - ");
				writer.append(value);
				value = Integer.toString(whatToCraft.get(j+3));
				writer.append(" крошки - ");
				writer.append(value);
				writer.append(System.lineSeparator());
			}
			
			writer.flush();
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		// Перезаписываю созданные руны в массив.
		int[] finalResult = new int[craftRunes.size()];
		for(int i = 0; i < (craftRunes.size()); i++) {
			finalResult[i] = craftRunes.get(i);
		}
		//System.out.println(finalResult[0] + " finalResult");
		System.out.println("Количество итераций: " + countIter);
		return finalResult;
	}
}


class HowToCraftRunes {
	public static void main(String args[]) {
		// 598800, 5157  331264, 2970
		Calculation test = new Calculation(445824, 2664);
		int[] result = test.parser();
		
		System.out.println("Result: " + result[0]);
		System.out.println("Золотой пыли потребовалось: " + result[1] + 
				" \nКристаллической крошки потребовалось: " + result[2]);
		
		System.out.println("Были скрафчены следующие руны:");
		for(int i = 3; i < result.length; i += 2)
			if (result[i+1] > 0)
				System.out.print(" || Ур. рун - " + result[i] + 
						" Кол-во - " + result[i+1]);
	}
}