import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.Random;

public class RSAdemo extends java.applet.Applet implements ActionListener {

Label pqLabel;

TextArea pTextArea;
Label pLabel;

TextArea qTextArea;
Label qLabel;

Button generate_pqButton;
Label generate_pq_sizeLabel;
TextField generate_pq_sizeField;

Label nLabel;
TextArea nTextArea;

Label nSpacer;
Button calculate_nButton;

Label eLabel;
TextArea eTextArea;
Label eSpacer;
Button generate_eButton;
Label generate_e_sizeLabel;
TextField generate_e_sizeField;

Label dLabel;
TextArea dTextArea;
Label dSpacer;
Button calculate_dButton;

Label useSpacer;

TextArea plainTextArea;

Button convert_to_numberButton;
Label convertButtonSpacer;
Button convert_to_textButton;

TextArea mTextArea;

Button encryptButton;
Label cryptButtonSpacer;
Button decryptButton;

TextArea cTextArea;

// This is the certainty that the bigInteger class will generate a prime number.  It's currently 
// set at 30 which means the odds of being a prime is 1-2^30 or about one in a million of it being
// non-prime.  If this number is increased the time to generate a prime goes up.

int prime_certainty = 30;

public void init()
{
	//The white background matches the web page background 
	setBackground (Color.white);
	
	//The flowLayout is left justified.  I used 0 instead of LEFT because I couldn't get the
	// LEFT constant to work.
  	setLayout(new FlowLayout(0));
  	
	//I really struggled with the layout of this applet.  It took almost as long to 
	// get the UI looking right than it did to program the RSA.  I tried static placement
	// using .setBounds but then everything would look the wrong size on different
	// platforms (the correct size on HP-Unix Netscape looks bad on a Mac or PC).  Also,
	// because everything changes size on different platforms, I couldn't rely on control
	// placement on the screen. So I ended up using FlowLayout and tricking it into putting
	// items on different lines by packaging a group of components on into it's own panel.
	// If anyone has a better way to do this please email me.
  	  	
	pqLabel = new Label("Enter prime 'p' and 'q' values or use the button below to generate them:");
  	add(pqLabel);
  	
	Panel pPanel = new Panel();
  	pLabel = new Label("p:");
  	pPanel.add(pLabel);
	pTextArea = new TextArea("", 2, 50, 1);
	pPanel.add(pTextArea);
	add(pPanel);
	
	Panel qPanel = new Panel();
	qLabel = new Label("q:");
  	qPanel.add(qLabel);
	qTextArea = new TextArea("", 2, 50, 1);
  	qPanel.add(qTextArea);
  	add(qPanel);
	
	Panel generate_pqPanel = new Panel();
	generate_pqButton = new Button("Generate p and q");
	generate_pqPanel.add(generate_pqButton);
	generate_pq_sizeLabel = new Label("which are of average bit size:");
	generate_pqPanel.add(generate_pq_sizeLabel);
	generate_pq_sizeField = new TextField("16", 5);
	generate_pqPanel.add(generate_pq_sizeField);
	add(generate_pqPanel);
	
	Panel nTextPanel = new Panel();
	nLabel = new Label("n:");
	nTextPanel.add(nLabel);
	nTextArea = new TextArea("", 4, 50, 1);
	nTextPanel.add(nTextArea);
	add(nTextPanel);
	
	Panel nPanel = new Panel();
	calculate_nButton = new Button("Calculate n");
	nPanel.add(calculate_nButton);
	nSpacer = new Label("                                                              ");
	nPanel.add(nSpacer);
	add(nPanel);
	
	Panel eTextPanel = new Panel();
	eLabel = new Label("e:");
	eTextPanel.add(eLabel);
	eTextArea = new TextArea("", 2, 50, 1);
	eTextPanel.add(eTextArea);
	add (eTextPanel);
	
	Panel ePanel = new Panel();
	generate_eButton = new Button("Generate e");
	ePanel.add(generate_eButton);
	generate_e_sizeLabel = new Label("which is of bit size:");
	ePanel.add(generate_e_sizeLabel);
	generate_e_sizeField = new TextField("8", 5);
	ePanel.add(generate_e_sizeField);
	eSpacer = new Label("                                        ");
	ePanel.add(eSpacer);
	add(ePanel);
	
	Panel dTextPanel = new Panel();
	dLabel = new Label("d:");
	dTextPanel.add(dLabel);
	dTextArea = new TextArea("", 2, 50, 1);
	dTextPanel.add(dTextArea);
	add(dTextPanel);
	
	Panel dPanel = new Panel();
	calculate_dButton = new Button("Calculate d");
	dPanel.add(calculate_dButton);
	dSpacer = new Label("                                                          ");
	dPanel.add(dSpacer);
  	add(dPanel);
  	
  	useSpacer = new Label("Enter text, numbers or encoded numbers below.                                                  ");
  	add(useSpacer);
  	
	plainTextArea = new TextArea("Plain text message", 4, 54, 1);
	add(plainTextArea);
	
	Panel convertButtonPanel = new Panel();
	convert_to_numberButton = new Button("Convert to Number");
	convertButtonPanel.add(convert_to_numberButton);
	convertButtonSpacer = new Label("                                            ");
	convertButtonPanel.add(convertButtonSpacer);
	convert_to_textButton = new Button("Convert to Text");
	convertButtonPanel.add(convert_to_textButton);
	add(convertButtonPanel);
	
	mTextArea = new TextArea("Numerical message", 4, 54, 1);
	add(mTextArea);
	
	Panel cryptButtonPanel = new Panel();
	encryptButton = new Button("Encrypt") ;
	cryptButtonPanel.add(encryptButton);
	cryptButtonSpacer = new Label("                                                                            ");
	cryptButtonPanel.add(cryptButtonSpacer);
	decryptButton = new Button("Decrypt") ;
	cryptButtonPanel.add(decryptButton);
	add(cryptButtonPanel);
	
	cTextArea = new TextArea("Encoded numerical message", 4, 54, 1);
  	add(cTextArea);
  
	generate_pqButton.addActionListener(this);
	calculate_nButton.addActionListener(this);
	generate_eButton.addActionListener(this);
	calculate_dButton.addActionListener(this);
	convert_to_numberButton.addActionListener(this);
	convert_to_textButton.addActionListener(this);
	encryptButton.addActionListener(this);
	decryptButton.addActionListener(this);
}


public void actionPerformed(ActionEvent event) {  
	
	//If the "generate pq" button is pushed then get the desired size of p and q
	// then let the BigInteger class generate the prime.  The size of p and q
	// is offset so that we can guarantee that p and q will not be too close 
	// to each other.  This make guessing p and q by searching values next to the 
	// square root of n more difficult.
	
	if (event.getSource() == generate_pqButton) {
		int pq_size = new Integer(generate_pq_sizeField.getText()).intValue();
		
		if (pq_size >= 4) {
			qTextArea.setText(new BigInteger(pq_size + 1, prime_certainty, new Random()).toString());
			pTextArea.setText(new BigInteger(pq_size - 1, prime_certainty, new Random()).toString());
		}
		else {
			pTextArea.setText("Enter larger p and q size.");
		}
	}
	
	//Send the data from p and q to the calculate n function
	
	else if (event.getSource() == calculate_nButton) {
		nTextArea.setText(calculate_n(new BigInteger(pTextArea.getText()),new BigInteger(qTextArea.getText())).toString());	
	}
	
	//Send the data from p, q and the size of e to the generate_e function
	
	else if (event.getSource() == generate_eButton) {
		eTextArea.setText(generate_e(new BigInteger(pTextArea.getText()),new BigInteger(qTextArea.getText()),new Integer(generate_e_sizeField.getText()).intValue()).toString());
	}
	
	//Send data from p, q and e to the calculate_d function
	
	else if (event.getSource() == calculate_dButton) {
		dTextArea.setText(
			calculate_d(new BigInteger(pTextArea.getText()),new BigInteger(qTextArea.getText()),new BigInteger(eTextArea.getText())).toString());
	}

	//This converts the plain text string into a number by reading the string in,
	// converting to bytes (ascii), then converting these bytes into a BigInteger.
	//The opposite happens in the next function.
	
	else if (event.getSource() == convert_to_numberButton) {
		mTextArea.setText(new BigInteger(plainTextArea.getText().getBytes()).toString());
	}
  
	else if (event.getSource() == convert_to_textButton) {
		plainTextArea.setText(new String(new BigInteger(mTextArea.getText()).toByteArray()));
	}
  
  	//Send the data to be encrypted and decrypted.
  	
	else if (event.getSource() == encryptButton) {
		cTextArea.setText(encrypt(new BigInteger(mTextArea.getText()),new BigInteger(eTextArea.getText()),new BigInteger(nTextArea.getText())).toString());
	}
  
	else if (event.getSource() == decryptButton) {
		mTextArea.setText(decrypt(new BigInteger(cTextArea.getText()),new BigInteger(dTextArea.getText()),new BigInteger(nTextArea.getText())).toString());
	}
}


//To generate e first phi(pq) (which is equal to phi(n)) is calculated.
// This is equal to (p-1)*(q-1).  Then the loop searches for psudo-randomly
// generated e of a specified size until one is found that is relatively
// prime to phi(pq) (gdc(e,phi_pq) = 1).  I used the generate random
// prime function because it guarantees a specific bit size of the number 
// it returns.  The regular method of generating a psuedo-random number
// only guarantees the number is between 0 and 2^n-1.  Another way would 
// be just add a one to the front of a random number, but I was too lazy 
// to do that.  So I set the primality certainty to 0 because I don't 
// care if e is prime.
//The last line is to ensure it does not go into a infinite loop
// if a e cannot be found for that bit size.

BigInteger generate_e(BigInteger p, BigInteger q, int bitsize) {
	BigInteger e, phi_pq;
    
	e = new BigInteger("0");
	phi_pq = q.subtract(new BigInteger("1"));
	phi_pq = phi_pq.multiply(p.subtract(new BigInteger("1")));
  	
  	int i = 0;
  	
  	do {
  		e = (new BigInteger(bitsize, 0, new Random())).setBit(0);
  		i = i + 1;
  	} while( i<100 && (e.gcd(phi_pq).compareTo(new BigInteger("1")) != 0));
  		
  
	return e;
}
 
 
//Once again the BigInteger class saves the programmer a lot of work.
// I calculate phi_pq ((p-1)*(q-1)) and then let the modInverse function 
// do the hard part of finding e^(-1) mod phi_pq.
 
BigInteger calculate_d(BigInteger p, BigInteger q, BigInteger e) {
	BigInteger d, phi_pq;
    
	phi_pq = q.subtract(new BigInteger("1"));
	phi_pq = phi_pq.multiply(p.subtract(new BigInteger("1")));
  
	d = e.modInverse(phi_pq);
	return d;
}


//Returns n=p*q

BigInteger calculate_n(BigInteger p, BigInteger q) {
	return p.multiply(q);
}


//This is a little tricky because the user is allowed to choose
// the size of n.  The value to be encrypted must be less than n.
// So first I find the bit size of n, then subract one, and that is the
// size of the message that I will encrypt at one time.  This ensures 
// the message chunk that is encrypted is smaller than n.  I use a
// mask to take one chunk of message at a time.  Then the chunk is
// encrypted and placed in the result c.  During the next iteration
// the message is shifted right and the result is shifted left and combined
// with c.  The encrypted chunk must be the same bit size as n so that no
// data is lost. 
// Encryption is done using the modPow function provide by the BigInt class.

BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n) {
	BigInteger c, bitmask;
	c = new BigInteger("0");
	int i = 0;
	bitmask = (new BigInteger("2")).pow(n.bitLength()-1).subtract(new BigInteger("1"));
	while (m.compareTo(bitmask) == 1) {
		c = m.and(bitmask).modPow(e,n).shiftLeft(i*n.bitLength()).or(c);
		m = m.shiftRight(n.bitLength()-1);
		i = i+1;
	}
	c = m.modPow(e,n).shiftLeft(i*n.bitLength()).or(c);
	return c;
}


//Decryption is done just as encryption above, only now the data is read in
// in chunks the same size as n, and the result, if correct, will be one bit 
// less than the size of n (because that was the original chuck size).

BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
	BigInteger m, bitmask;
	m = new BigInteger("0");
	int i = 0;
	bitmask = (new BigInteger("2")).pow(n.bitLength()).subtract(new BigInteger("1"));
	while (c.compareTo(bitmask) == 1) {
		m = c.and(bitmask).modPow(d,n).shiftLeft(i*(n.bitLength()-1)).or(m);
		c = c.shiftRight(n.bitLength());
		i = i+1;
	}
	m = c.modPow(d,n).shiftLeft(i*(n.bitLength()-1)).or(m);
	
  	return m;
}
 
}