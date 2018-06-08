package com.kljs.rabbitmq;

import com.kljs.rabbitmq.consumer.DQConsumer;
import com.kljs.rabbitmq.producer.DQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

//    @Autowired
//    private DQProducer clickProducer;

    @Autowired
    private DQConsumer clickConsumer;

    @Override
    public void run(String... args) throws Exception {

//        List<String> list = new ArrayList<>();
//        list.add("火星发现有机分子 火星上真有外星人吗？   本周，美国宇航局的科学家们在“科学”（Science）杂志上发表了两篇关于火星的研究论文，并通过现场直播向全世界解释了其中的发现。那么，火星上真有外星人吗？嗯，美国宇航局没完全找到，不过离真相也越来越近了。");
//        list.add("美国宇航局宣布好奇号（Curiosity）探测车在岩层中发现了科学家所谓的“有机分子”。这些岩层可追溯到近40亿年前，那时的火星应该是个更适宜生命存活的地方。这一发现简直振奋人心，令人难以置信！");
//        list.add("美国宇航局很快指出，证明火星上曾有过生命存在的证据仍有待商榷。火星上发现的有机化合物可能确实来自数十亿年前火星表面存在的生命，但这并不是唯一的来源。");
//        list.add("研究人员指出，这些化合物可能是通过其他自然过程形成的。报告中还称，美国宇航局对火星大气中的甲烷进行了超级大规模的检测，发现了这些特殊的化合物。科学家们使用高性能仪，探测到大气中甲烷的含量每个季节都在大幅上升，但甲烷的来源仍是个谜。");
//
//        for(String msg : list) {
//            clickProducer.send(msg);
//        }

        while (true) {
            String msg = clickConsumer.consumer();
            if(StringUtils.isEmpty(msg)) {
                continue;
            }
            System.out.println("fetch：" + msg);
        }


    }
}
