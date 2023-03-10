package com.becks.uniquedungeons.common.blocks.cracked_blocks;

import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class CrackingPattern {
    List<Direction> content = new ArrayList();

    public int test(Direction d){
        if (content.contains(d.getOpposite())){
            return -1;
        }
        if (!content.contains(d)){
            return 1;
        }
        return 2;
    }

    public CrackingPattern copy(){
        CrackingPattern copy = new CrackingPattern();
        for (Direction d : content){
            copy.add(d);
        }
        return copy;
    }

    public CrackingPattern add(Direction d){
        if (!content.contains(d)){
            content.add(d);
        }
        return this;
    }
}
