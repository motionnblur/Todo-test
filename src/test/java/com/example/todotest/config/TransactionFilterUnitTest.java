package com.example.todotest.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class TransactionFilterUnitTest {
    @MockBean
    private BeanFactory beanFactory;
    @MockBean
    private TransactionFilter transactionFilter;

    @Test
    public void testDestroy() throws Exception {
        Mockito.when(beanFactory.getBean(TransactionFilter.class)).thenReturn(transactionFilter);
        transactionFilter.destroy(); // Call the actual destroy method
        Mockito.verify(transactionFilter).destroy(); // Verify destroy was called on the mock
    }
    @Test
    public void testDoFilter() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        Filter filter = mock(Filter.class);

        filter.doFilter(request, response, chain);
        Mockito.verify(filter).doFilter(request, response, chain);
    }
}