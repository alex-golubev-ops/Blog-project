package com.leverx.blog.service;

import com.leverx.blog.entity.Tag;
import org.hamcrest.core.IsIterableContaining;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void parseToTagsTest(){
        String testTags = "tag1 tag2 tag3";
        Set<Tag> tags = tagService.parseToTags(testTags);
        Assert.assertThat(tags, IsIterableContaining
                .hasItems(new Tag("tag1"),new Tag("tag2"),new Tag("tag3")));
    }



}