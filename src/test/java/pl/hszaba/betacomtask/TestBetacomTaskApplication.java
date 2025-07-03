package pl.hszaba.betacomtask;

import org.springframework.boot.SpringApplication;

public class TestBetacomTaskApplication {

    public static void main(String[] args) {
        SpringApplication.from(BetacomTaskApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
