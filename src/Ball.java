import java.awt.*;

//novo import, necessário para criar números random
import java.util.*;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {

	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/

	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private double speed;

	//variáveis novas
	private int directionX; //responsável por salvar a direção do eixo X da bola. 1 é para a direita, 2 é para a esquerda;
	private int directionY; //responsável por salvar a direção do eixo Y da bola. 1 é para cima, 2 é para baixo;


	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;

		//a bola começa o jogo saindo para direções aleatórias
		Random gerador = new Random();
		directionX = (gerador.nextInt(2))+1;
		directionY = (gerador.nextInt(2))+1;

	}


	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){

		GameLib.setColor(Color.YELLOW);
		GameLib.fillRect(cx, cy, width, height);

	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.

		@param delta quantidade de millisegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/

	public void update(long delta){

		if (directionY == 1){//se está andando para cima
			cy = cy - (delta*speed);
			if(directionX == 1){//se está andando para a direita
				cx = cx + (delta*speed);
			}else{//se está andando para a esquerda
				cx = cx - (delta*speed);
			}
		}else{//se está andando para baixo
			cy = cy + (delta*speed);
			if(directionX == 1){//se está andando para a direita
				cx = cx + (delta*speed);
			}else{//se está andando para a esquerda
				cx = cx - (delta*speed);
			}
		}
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.

		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/

	public void onPlayerCollision(String playerId){
		if(playerId.equals(Pong.PLAYER1)){//se bateu no player 1, muda a direção da bola para a direita
			directionX = 1;
		}
		if(playerId.equals(Pong.PLAYER2)){//se bateu no player 2, muda a direção da bola para a esquerda
			directionX = 2;
		}
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){

		if(wallId.equals(Pong.TOP)){
			directionY = 2;
		}
		if(wallId.equals(Pong.RIGHT)){
			directionX = 2;

		}
		if(wallId.equals(Pong.LEFT)){
			directionX = 1;
		}
		if(wallId.equals(Pong.BOTTOM)){
			directionY = 1;
		}
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/

	public boolean checkCollision(Wall wall){

		if(wall.getId().equals(Pong.TOP) && cy < wall.getCy() + wall.getHeight()){
			return true;
		}
		if(wall.getId().equals(Pong.RIGHT) && cx > wall.getCx() - wall.getWidth()){
			return true;
		}
		if(wall.getId().equals(Pong.LEFT) && cx < wall.getCx() + wall.getWidth()){
			return true;
		}
		if(wall.getId().equals(Pong.BOTTOM) && cy > wall.getCy() - wall.getHeight()){
			return true;
		}


		return false;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/

	public boolean checkCollision(Player player){

		//se a bola estiver dentro da área do player recebido como parâmetro, ele retornará true. O teste do id é para que não entre duas vezes o código (há uma execução para cada player), o que resultaria em problemas
		//a área calculada leva em consideração o tamanho das bordas do jogador, por isso o uso de somas e subtrações de width/2 do player e da bola
		if(player.getId().equals(Pong.PLAYER1) && cx - (width/2) < player.getCx() + (player.getWidth()/2) && cx + (width/2) > player.getCx() - (player.getWidth()/2) && cy < player.getCy()+(player.getHeight()/2) && cy > player.getCy()-(player.getHeight()/2)){
			return true;
		}
		if(player.getId().equals(Pong.PLAYER2) && cx - (width/2) < player.getCx() + (player.getWidth()/2) && cx + (width/2)> player.getCx() - (player.getWidth()/2) && cy < player.getCy()+(player.getHeight()/2) && cy > player.getCy()-(player.getHeight()/2)){
			return true;
		}

		return false;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/

	public double getCx(){

		return cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){

		return cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){

		return speed;
	}

}
