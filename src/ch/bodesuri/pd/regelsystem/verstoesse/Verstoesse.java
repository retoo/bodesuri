/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ch.bodesuri.pd.regelsystem.verstoesse;

/**
 * Sammelklasse für alle Verstösse, die nur eine Erklärung haben.
 */
public class Verstoesse {
	/* Allgemein */

	public static class AnzahlBewegungen extends RegelVerstoss {
		public AnzahlBewegungen() {
			super("Anzahl Bewegungen stimmt nicht.");
		}
	}

	public static class SoNichtFahren extends RegelVerstoss {
		public SoNichtFahren() {
			super(-0.5, "Man kann so nicht fahren.");
		}
	}

	/* Starten */

	public static class FalschStarten extends RegelVerstoss {
		public FalschStarten() {
			super("Man kann nur mit dem König oder dem Ass starten.");
		}
	}

	public static class NurMitFigurVomLagerStarten extends RegelVerstoss {
		public NurMitFigurVomLagerStarten() {
			super("Man kann nur mit einer Figur vom Lager starten.");
		}
	}

	public static class NurAufEigeneBankStarten extends RegelVerstoss {
		public NurAufEigeneBankStarten() {
			super(0.5, "Man kann nur auf die eigene Bank starten.");
		}
	}

	public static class AufGeschuetzteFigurStarten extends RegelVerstoss {
		public AufGeschuetzteFigurStarten() {
			super(0.55, "Man nicht starten, wenn eine geschützte Figur auf der Bank steht.");
		}
	}

	/* Tauschen */

	public static class FalscheFigurenTauschen extends RegelVerstoss {
		public FalscheFigurenTauschen() {
			super("Man muss eine eigene und eine fremde Figur tauschen.");
		}
	}

	public static class GeschuetzteTauschen extends RegelVerstoss {
		public GeschuetzteTauschen() {
			super("Man kann keine geschützten Figuren tauschen.");
		}
	}

	/* Fahren */

	public static class ImLagerFahren extends RegelVerstoss {
		public ImLagerFahren() {
			super("Man kann nicht im Lager fahren.");
		}
	}

	public static class InsLagerFahren extends RegelVerstoss {
		public InsLagerFahren() {
			super("Man kann nicht ins Lager fahren.");
		}
	}

	public static class ImHimmelNurVorwaertsFahren extends RegelVerstoss {
		public ImHimmelNurVorwaertsFahren() {
			super("Man kann im Himmel nur vorwärts fahren.");
		}
	}

	public static class RueckwaertsInHimmelFahren extends RegelVerstoss {
		public RueckwaertsInHimmelFahren() {
			super("Man kann nicht rückwärts in den Himmel fahren.");
		}
	}

	public static class NurRueckwaertsFahren extends RegelVerstoss {
		public NurRueckwaertsFahren() {
			super("Man kann nur rückwärts fahren.");
		}
	}

	public static class NurVorwaertsFahren extends RegelVerstoss {
		public NurVorwaertsFahren() {
			super("Man kann nur vorwärts fahren.");
		}
	}

	public static class MitFigurFahren extends RegelVerstoss {
		public MitFigurFahren() {
			super("Man muss mit einer Figur fahren.");
		}
	}

	public static class MitEigenerFigurFahren extends RegelVerstoss {
		public MitEigenerFigurFahren() {
			super("Man muss mit einer eigenen Figur fahren.");
		}
	}

	public static class AufOderUeberGeschuetzteFahren extends RegelVerstoss {
		public AufOderUeberGeschuetzteFahren() {
			super(2.0, "Man kann nicht auf oder über eine geschützte Figur fahren.");
		}
	}

	public static class UeberFigurAufBankFahren extends RegelVerstoss {
		public UeberFigurAufBankFahren() {
			super(2.0, "Man kann nicht über eine Figur auf ihrer Bank fahren.");
		}
	}

	public static class NurInEigenenHimmelFahren extends RegelVerstoss {
		public NurInEigenenHimmelFahren() {
			super("Man kann nur in den eigenen Himmel fahren.");
		}
	}

	public static class DirektInHimmelFahren extends RegelVerstoss {
		public DirektInHimmelFahren() {
			super("Man kann nicht direkt nach dem Start in den Himmel fahren.");
		}
	}
}
