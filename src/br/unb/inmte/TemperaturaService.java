package br.unb.inmte;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TemperaturaService {

	private Map<String, List<TemperaturaTO>> mapTemperatura;
	private static Scanner leituraTeclado;

	public TemperaturaService() {
		super();
		mapTemperatura = new HashMap<String, List<TemperaturaTO>>();
		leituraTeclado = new Scanner(System.in);
	}

	public Map<String, List<TemperaturaTO>> getMapTemperatura() {
		return mapTemperatura;
	}

	public void setMapTemperatura(Map<String, List<TemperaturaTO>> mapTemperatura) {
		this.mapTemperatura = mapTemperatura;
	}

	public String gerarChave(int mes, int ano) {
		return String.valueOf(ano) + "-" + String.valueOf(mes);
	}

	public double converterDouble(double valor) {
		return Double.parseDouble(String.format("%.2f", valor).replace(",", "."));
	}

	public double gerarTemperatura(int maximo, int minimo) {
		double numero = (double) Math.random() * (maximo - minimo) + minimo;
		return converterDouble(numero);
	}

	public int obterDiasMes(int mes, int ano) {
		Calendar data = (Calendar) Calendar.getInstance().clone();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH, mes - 1); // 0 - Janeiro, 1 - Ferveiro, 2 - Março, ...
		return data.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public TemperaturaTO gerarTemperatura(int dia, int mes, int ano, double media) {

		TemperaturaTO temperaturaTO = new TemperaturaTO();
		temperaturaTO.setAno(ano);
		temperaturaTO.setMes(mes);
		temperaturaTO.setDia(dia);
		temperaturaTO.setMediaTemperatura(media);
		return temperaturaTO;
	}

	public void cargaInicial() {
		int ano = 2020, mes = 1;
		int diaInicial = 1, diaFinal = 31;
		int temperaturaMinima = -10, temperaturaMaxima = 50;
		String chave = gerarChave(mes, ano);
		List<TemperaturaTO> listaTemperatura = new ArrayList<TemperaturaTO>();

		for (int i = diaInicial; i <= diaFinal; i++) {
			TemperaturaTO temperaturaTO = new TemperaturaTO();
			temperaturaTO.setDia(i);
			temperaturaTO.setAno(ano);
			temperaturaTO.setMes(mes);
			temperaturaTO.setMediaTemperatura(gerarTemperatura(temperaturaMaxima, temperaturaMinima));
			listaTemperatura.add(temperaturaTO);
		}

		if (!mapTemperatura.containsKey(chave))
			mapTemperatura.put(chave, listaTemperatura);
	}

	public void imprimirTemperatura(int mes, int ano) {
		String chave = gerarChave(mes, ano);

		if (mapTemperatura.containsKey(chave)) {
			List<TemperaturaTO> lista = mapTemperatura.get(chave);
			System.out.printf("Ano: %d Mês: %d\n", ano, mes);

			for (TemperaturaTO temperatura : lista) {
				System.out.printf("\tDia: %d Média da Temperatura: %.2f °C\n\n", temperatura.getDia(),
						temperatura.getMediaTemperatura());
			}

		} else {
			System.out.println("Temperatura não Cadastrada no Mês e Ano");
		}

	}

	public void imprimirTemperatura(int mes, int ano, double mediaTemperatura) {

		System.out.printf("Ano: %d Mês: %d\n", ano, mes);
		System.out.printf("\tMédia da Temperatura: %.2f °C\n\n", mediaTemperatura);

	}

	public void imprimirTemperatura(int mes, int ano, List<TemperaturaTO> listaTemperatura, EnumTipoTemperatura tipo) {

		if (tipo == EnumTipoTemperatura.MINIMO)
			System.out.printf("Temperatura Minima do Mês: %d Ano: %d\n", mes, ano);
		else {
			System.out.printf("Temperatura Maxima do Mês: %d Ano: %d\n", mes, ano);
		}

		for (TemperaturaTO temperatura : listaTemperatura)
			System.out.printf("\tDia: %d Temperatura: %.2f °C\n\n", temperatura.getDia(),
					temperatura.getMediaTemperatura());
	}

	public void entradaTemperaturas(int mes, int ano) {

		List<TemperaturaTO> listaTemperatura = new ArrayList<TemperaturaTO>();
		TemperaturaTO temperaturaTO;

		int dia = 0;
		double temperatura = 0;
		String chave = gerarChave(mes, ano);

		dia = obterDiasMes(mes, ano);

		for (int i = 1; i <= dia; i++) {

			do {
				try {
					System.out.println(
							"Digite a Temperatura entre -10 e 50 do Dia " + i + " ou 999 para sai ou 888 pular o dia");
					temperatura = leituraTeclado.nextDouble();
					if (temperatura == 888)
						i++;
						continue;
				} catch (Exception e) {
					System.out.println("Dados Invalidos digite novamente");
				}
			} while ((temperatura < -10 || temperatura > 50) && temperatura != 999);

			if (temperatura != 999) {
				temperaturaTO = gerarTemperatura(i, mes, ano, converterDouble(temperatura));
				listaTemperatura.add(temperaturaTO);
			} else {
				break;
			}
		}

		if (!mapTemperatura.containsKey(chave))
			mapTemperatura.put(chave, listaTemperatura);
		else {
			List<TemperaturaTO> listaVelha = mapTemperatura.get(chave);
			listaVelha.addAll(listaTemperatura);
		}

	}

	public double obterMediaTemperatura(int mes, int ano) {
		String chave = gerarChave(mes, ano);
		double soma = 0, media = 0;
		int contador = 0;

		if (mapTemperatura.containsKey(chave)) {
			List<TemperaturaTO> lista = mapTemperatura.get(chave);

			for (TemperaturaTO temperatura : lista) {
				double numero = temperatura.getMediaTemperatura();
				soma += numero;
				contador++;
			}

			if (contador > 0) {
				media = soma / contador;
			}

			return converterDouble(media);

		} else {
			System.out.println("Temperatura não Cadastrada no Mês e Ano");
		}

		return 0;
	}

	public ArrayList<TemperaturaTO> obterTemperaturaMinimaMaximo(int mes, int ano, EnumTipoTemperatura tipo) {
		String chave = gerarChave(mes, ano);

		ArrayList<TemperaturaTO> listaTemperaturaMaxima = new ArrayList<TemperaturaTO>();
		ArrayList<TemperaturaTO> listaTemperaturaMinima = new ArrayList<TemperaturaTO>();

		if (mapTemperatura.containsKey(chave)) {
			List<TemperaturaTO> lista = mapTemperatura.get(chave);
			double minima = lista.get(0).getMediaTemperatura();
			double maxima = lista.get(0).getMediaTemperatura();

			for (TemperaturaTO temperatura : lista) {
				double numero = temperatura.getMediaTemperatura();

				if (numero < minima) {
					minima = temperatura.getMediaTemperatura();
					listaTemperaturaMinima.clear();
					listaTemperaturaMinima.add(temperatura);
				} else if (numero == minima) {
					minima = temperatura.getMediaTemperatura();
					listaTemperaturaMinima.add(temperatura);
				}

				if (numero > maxima) {
					maxima = temperatura.getMediaTemperatura();
					listaTemperaturaMaxima.clear();
					listaTemperaturaMaxima.add(temperatura);
				} else if (numero == maxima) {
					maxima = temperatura.getMediaTemperatura();
					listaTemperaturaMaxima.add(temperatura);
				}
			}

			if (EnumTipoTemperatura.MAXIMO.equals(tipo)) {
				return listaTemperaturaMaxima;
			} else {
				return listaTemperaturaMinima;
			}

		} else {
			System.out.println("Temperatura não Cadastrada no Mês e Ano");
		}

		return null;
	}

}
