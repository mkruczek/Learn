package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class MyDBGenerator {


    public static void main(String[] args) {

        Schema schema = new Schema(7, "pl.michalkruczek.learn.db");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addTables(Schema schema){

        Entity category = schema.addEntity("Category");
        category.addIdProperty().primaryKey().autoincrement();
        category.addStringProperty("value");

        Entity question = schema.addEntity("Question");
        question.addIdProperty().primaryKey().autoincrement();
        Property categoryIdProperty = question.addLongProperty("categoryId").getProperty();
        question.addToOne(category, categoryIdProperty);
        question.addStringProperty("question");
        question.addStringProperty("answer");
        question.addStringProperty("describe");
        question.addDateProperty("addDate");
        question.addDateProperty("nextRepeat");
        question.addIntProperty("level");
        question.addStringProperty("history");



    }



}
