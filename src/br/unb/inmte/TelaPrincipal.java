package br.unb.inmte;

import java.util.ArrayList;
import java.util.Scanner;

public class TelaPrincipal {

	TemperaturaService temperaturaService;
	private static Scanner leituraTeclado;

	public TelaPrincipal() {
		super();
		temperaturaService = new TemperaturaService();
		leituraTeclado = new Scanner(System.in);
	}

	public int entradaAno() {
		int ano = 0;

		System.out.println("Digite Ano no Intervalo 2011-2020 ou 999 para sair");

		do {
			try {
				ano = leituraTeclado.nextInt();
			} catch (Exception e) {
				System.out.println("Dados Invalidos");
			}
		} while ((ano < 2010 || ano > 2021) && ano != 999);

		return ano;
	}

	public int entradaMes() {
		int mes = 0;
		System.out.println("Digite Mes no Intervalo 1-12 ou 999 para sair");

		do {
			try {
				mes = leituraTeclado.nextInt();
			} catch (Exception e) {
				System.out.println("Dados Invalidos");
			}
		} while ((mes <= 0 || mes >= 13) && mes != 999);

		return mes;
	}

	public static void main(String[] args) {
		TelaPrincipal principal = new TelaPrincipal();

		int opcao = 0;
		int ano = 0, mes = 0;

		try {
			
			// Fun��o cargaInicial cadastre automaticamente dados aleat�rios 
			// no m�s de Janeiro de 2020, conforme a especifica��o no final do TP1.
			principal.temperaturaService.cargaInicial();
			ArrayList<TemperaturaTO> listaMinimaMaximo = new ArrayList<TemperaturaTO>();
			while (opcao != 6) {

				System.out.println("1 - Entrada das temperaturas");
				System.out.println("2 - Calculo da temperatura m�dia");
				System.out.println("3 - Calculo da temperatura m�nima");
				System.out.println("4 - Calculo da temperatura m�xima");
				System.out.println("5 - Relat�rio meteorol�gico");
				System.out.println("6 - Sair");
				opcao = leituraTeclado.nextInt();

				if (opcao != 6) {
					ano = principal.entradaAno();
					mes = principal.entradaMes();
				}

				switch (opcao) {
				case 1:

					if (ano == 999 || mes == 999) {
						System.out.println("Entrada Incompleta m�s ou ano");
					} else {
						principal.temperaturaService.entradaTemperaturas(mes, ano);
					}

					break;

				case 2:

					if (ano == 999 || mes == 999) {
						System.out.println("Entrada Incompleta m�s ou ano");
					} else {
						double mediaTemperatura = principal.temperaturaService.obterMediaTemperatura(mes, ano);
						principal.temperaturaService.imprimirTemperatura(mes, ano, mediaTemperatura);
					}

					break;

				case 3:

					if (ano == 999 || mes == 999) {
						System.out.println("Entrada Incompleta m�s ou ano");
					} else {
						listaMinimaMaximo = principal.temperaturaService.obterTemperaturaMinimaMaximo(mes, ano,
								EnumTipoTemperatura.MINIMO);
						principal.temperaturaService.imprimirTemperatura(mes, ano, listaMinimaMaximo,
								EnumTipoTemperatura.MINIMO);
					}

					break;

				case 4:

					if (ano == 999 || mes == 999) {
						System.out.println("Entrada Incompleta m�s ou ano");
					} else {
						listaMinimaMaximo = principal.temperaturaService.obterTemperaturaMinimaMaximo(mes, ano,
								EnumTipoTemperatura.MAXIMO);
						principal.temperaturaService.imprimirTemperatura(mes, ano, listaMinimaMaximo,
								EnumTipoTemperatura.MAXIMO);
					}

					break;

				case 5:

					if (ano == 999 || mes == 999) {
						System.out.println("Entrada Incompleta m�s ou ano");
					} else {
						System.out.println("Relat�rio das m�dias de temperatura de cada dia \n");
						principal.temperaturaService.imprimirTemperatura(mes, ano);

						System.out.printf("Relat�rio das m�dias de temperatura do m�s %d\n", mes);
						double mediaTemperatura = principal.temperaturaService.obterMediaTemperatura(mes, ano);
						principal.temperaturaService.imprimirTemperatura(mes, ano, mediaTemperatura);

						System.out.printf("Relat�rio da temperatura minima do m�s %d\n", mes);
						listaMinimaMaximo = principal.temperaturaService.obterTemperaturaMinimaMaximo(mes, ano,
								EnumTipoTemperatura.MINIMO);
						principal.temperaturaService.imprimirTemperatura(mes, ano, listaMinimaMaximo,
								EnumTipoTemperatura.MINIMO);

						System.out.printf("Relat�rio da temperatura maxima do m�s %d\n", mes);
						listaMinimaMaximo = principal.temperaturaService.obterTemperaturaMinimaMaximo(mes, ano,
								EnumTipoTemperatura.MAXIMO);
						principal.temperaturaService.imprimirTemperatura(mes, ano, listaMinimaMaximo,
								EnumTipoTemperatura.MAXIMO);
					}

					break;

				default:
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Erro no sistema\n");
		}

	}
}
