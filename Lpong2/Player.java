//parte gráfica
import java.awt.*;
import java.awt.image.*;

//erros
import java.io.*;
import javax.imageio.*;

/**
	Esta classe representa os jogadores (players) do jogo. A classe princial do jogo (Pong)
	instancia dois objetos deste tipo quando a execução é iniciada.
*/

public class Player {

	// variáveis utilizadas na mecânica do player
	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private String id;
	private double[] v_limit = new double[2];
	private Color lightyellow = new Color(249, 241, 143);
	//------------------------------

	//variáveis das imagens
	private BufferedImage player1 = null;
	private BufferedImage player2 = null;
	//------------------------------

	public Player(double cx, double cy, double width, double height, Color color, String id, double [] v_limit, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.id = id;
		this.v_limit = v_limit;

		try { //pega a imagem do player 1
				player1 = ImageIO.read(getClass().getResource("imagens/player1azulversao2.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		try { //pega a imagem do player 2
				player2 = ImageIO.read(getClass().getResource("imagens/player2verm.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}

	}


	public void draw(){

		if (id.equals(Pong.PLAYER1)){
			GameLib.g.drawImage(player1, (int)Math.round(cx) - (int)Math.round(width/2), (int)Math.round(cy) - (int)Math.round(height/2), null);
		}else{
			GameLib.g.drawImage(player2, (int)Math.round(cx) - (int)Math.round(width/2), (int)Math.round(cy) - (int)Math.round(height/2), null);
		}

	}


	public void moveUp(long delta){
		if (cy > v_limit[0] + (height/2)){//só anda se não bater nas paredes. o height/2 é de si mesmo
			cy = cy - (delta*Ball.speedplayer);
		}
	}


	public void moveDown(long delta){
		if (cy < v_limit[1] - (height/2)){//só anda se não bater nas paredes. o height/2 é de si mesmo
			cy = cy + (delta*Ball.speedplayer);
		}
	}


	public String getId() {

		return id;
	}


	public double getWidth() {

		return width;
	}


	public double getHeight() {

		return height;
	}


	public double getCx() {

		return cx;
	}


	public double getCy() {

		return cy;
	}
}
