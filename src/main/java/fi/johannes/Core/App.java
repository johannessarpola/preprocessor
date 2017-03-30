package fi.johannes.Core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@Component
@ComponentScan
class App {

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        App mainObj = ctx.getBean(App.class);
        mainObj.init();
    }

    void init(){
        System.out.println("Hello W");
    }
    // TODO Read corpus from a directory
    // TODO Perform the tf-idf indexing

}
