package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

public class CashRegisterOperationForm extends ConstrainedForm {
	
	private Button btnmc3;
	private Button btnmc5;
	private Button btnmc8;
	private Decimalbox decBalancemc3;
	private Decimalbox decBalancemc5;
	private Decimalbox decBalancemc8;
	private Decimalbox decEnteredAmountmc3;
	private Decimalbox decEnteredAmountmc5;
	private Decimalbox decEnteredAmountmc8;
	private Textbox txtInputExpression;
	private Decimalbox decInput;
	private Button btnCalc;
	private Decimalbox decCounted;
	private Button btnDeposit;
	private Button btnWithdraw;
	private Button btnCount;
	private Grid grd;

	public CashRegisterOperationForm(DesktopAgent desktop,
									Button btnmc3,
									Button btnmc5,
									Button btnmc8,
									Decimalbox decBalancemc3,
									Decimalbox decBalancemc5,
									Decimalbox decBalancemc8,
									Decimalbox decEnteredAmountmc3,
									Decimalbox decEnteredAmountmc5,
									Decimalbox decEnteredAmountmc8,
									Textbox txtInputExpression,
									Decimalbox decInput,
									Button btnCalc,
									Decimalbox decCounted,
									Button btnDeposit,
									Button btnWithdraw,
									Button btnCount,
									Grid grd) {
		
		this.setDesktop(desktop);
		this.setBtnmc3(btnmc3);
		this.setBtnmc5(btnmc5);
		this.setBtnmc8(btnmc8);
		this.setDecBalancemc3(decBalancemc3);
		this.setDecBalancemc5(decBalancemc5);
		this.setDecBalancemc8(decBalancemc8);
		this.setDecEnteredAmountmc3(decEnteredAmountmc3);
		this.setDecEnteredAmountmc5(decEnteredAmountmc5);
		this.setDecEnteredAmountmc8(decEnteredAmountmc8);
		this.setTxtInputExpression(txtInputExpression);
		this.setDecInput(decInput);
		this.setBtnCalc(btnCalc);
		this.setDecCounted(decCounted);
		this.setBtnDeposit(btnDeposit);
		this.setBtnWithdraw(btnWithdraw);
		this.setBtnCount(btnCount);
		this.grd = grd;
	}

	public Grid getGrd() {
		return grd;
	}

	public void setGrd(Grid grd) {
		this.grd = grd;
	}

	public Button getBtnmc3() {
		return btnmc3;
	}

	public void setBtnmc3(Button btnmc3) {
		this.btnmc3 = btnmc3;
	}

	public Button getBtnmc5() {
		return btnmc5;
	}

	public void setBtnmc5(Button btnmc5) {
		this.btnmc5 = btnmc5;
	}

	public Button getBtnmc8() {
		return btnmc8;
	}

	public void setBtnmc8(Button btnmc8) {
		this.btnmc8 = btnmc8;
	}

	public Decimalbox getDecBalancemc3() {
		return decBalancemc3;
	}

	public void setDecBalancemc3(Decimalbox decBalancemc3) {
		this.decBalancemc3 = decBalancemc3;
	}

	public Decimalbox getDecBalancemc5() {
		return decBalancemc5;
	}

	public void setDecBalancemc5(Decimalbox decBalancemc5) {
		this.decBalancemc5 = decBalancemc5;
	}

	public Decimalbox getDecBalancemc8() {
		return decBalancemc8;
	}

	public void setDecBalancemc8(Decimalbox decBalancemc8) {
		this.decBalancemc8 = decBalancemc8;
	}

	public Decimalbox getDecEnteredAmountmc3() {
		return decEnteredAmountmc3;
	}

	public void setDecEnteredAmountmc3(Decimalbox decEnteredAmountmc3) {
		this.decEnteredAmountmc3 = decEnteredAmountmc3;
	}

	public Decimalbox getDecEnteredAmountmc5() {
		return decEnteredAmountmc5;
	}

	public void setDecEnteredAmountmc5(Decimalbox decEnteredAmountmc5) {
		this.decEnteredAmountmc5 = decEnteredAmountmc5;
	}

	public Decimalbox getDecEnteredAmountmc8() {
		return decEnteredAmountmc8;
	}

	public void setDecEnteredAmountmc8(Decimalbox decEnteredAmountmc8) {
		this.decEnteredAmountmc8 = decEnteredAmountmc8;
	}

	public Textbox getTxtInputExpression() {
		return txtInputExpression;
	}

	public void setTxtInputExpression(Textbox txtInputExpression) {
		this.txtInputExpression = txtInputExpression;
	}

	public Decimalbox getDecInput() {
		return decInput;
	}

	public void setDecInput(Decimalbox decInput) {
		this.decInput = decInput;
	}

	public Button getBtnCalc() {
		return btnCalc;
	}

	public void setBtnCalc(Button btnCalc) {
		this.btnCalc = btnCalc;
	}

	public Decimalbox getDecCounted() {
		return decCounted;
	}

	public void setDecCounted(Decimalbox decCounted) {
		this.decCounted = decCounted;
	}

	public Button getBtnDeposit() {
		return btnDeposit;
	}

	public void setBtnDeposit(Button btnDeposit) {
		this.btnDeposit = btnDeposit;
	}

	public Button getBtnWithdraw() {
		return btnWithdraw;
	}

	public void setBtnWithdraw(Button btnWithdraw) {
		this.btnWithdraw = btnWithdraw;
	}

	public Button getBtnCount() {
		return btnCount;
	}

	public void setBtnCount(Button btnCount) {
		this.btnCount = btnCount;
	}
}
