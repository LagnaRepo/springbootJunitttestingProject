package com.yash.springdatajparestful.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(value = MockitoExtension.class)
public class DiffrenceBetweenSpyAndMock {
    @Spy
    private  ArrayList<String> alist;
//    @BeforeAll
//    public void setUp()
//    {// to anable the MockitoAnnotations manually.
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void spyWithoutAnnotation()
    {
       // ArrayList<String> alist = Mockito.spy(new ArrayList<>());
        alist.add("When I will Get Job");
        alist.add("Please God Understand My situation");
        alist.add("Its too expensive..");
        // the assertion passed because spy is partial mocking , it creats spy object on top of
       // the actual object and call its real methods if there is no stubbed method by tester.
        // stubbing a spy method
        Mockito.doReturn(50).when(alist).size();

        assertEquals(50, alist.size());


    }

    @Test
    public void mockWithoutAnnotation()
    {
        ArrayList<String> alist = Mockito.mock(ArrayList.class);
        alist.add("When I will Get Job");
        alist.add("Please God Understand My situation");
        alist.add("Its too expensive..");
        // the assertion filed because mock is total mocking , it creats mock object around
        // the actual class/interface and never call its real methods if there is no stubbed method by tester
        // and always returns null or default value if there is no stubbing present.
        assertEquals(3, alist.size());


    }

    @Test
    public void spyWithAnnotation() {
    }
}

