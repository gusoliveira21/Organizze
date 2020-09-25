package com.githubgusoliveira21.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {
    public static String dataAtual(){

        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    };

    public static String mesAnoDataEscolhida(String data){
        String retornoData[] = data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];

        String mesAno = mes + ano;
        return  mesAno;
    };


}
