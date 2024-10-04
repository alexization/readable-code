package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Advanced;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Advanced();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
        minesweeper.initialize();
        minesweeper.run();
    }


    /*
    * DIP (Dependency Inversion Principle)
    *
    * DI (Dependency Injection) - DI를 보면 숫자 "3" 을 떠올려야 한다.
    * 객체가 있고 또 다른 객체 하나가 있다. A라는 객체가 B라는 객체를 필요로 한다.
    * 그래서 이 둘이 의존관계를, 의존성을 갖고 싶은데 A가 B를 생성해서 사용하는게 아니라 의존성을 주입받고 싶어한다.
    *
    * -> 필요한 의존성을 내가 직접 생성하는 것이 아니라 외부에서 주입받겠다는 의미
    *
    * IoC (Inversion of Control)
    *
    *
    * */

}
