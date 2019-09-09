package com.example.quickindex;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.NavigableMap;

/**
 * Create by Politness Chen on 2019/8/23--15:43
 */
public class Person {

    private String name;

    private String pinyin;

    public Person(String name) throws BadHanyuPinyinOutputFormatCombination {
        this.name = name;
        this.pinyin = Pinyin4jUtils.toPinYinUppercaseInitials(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
