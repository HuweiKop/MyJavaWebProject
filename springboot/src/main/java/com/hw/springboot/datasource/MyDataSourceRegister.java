package com.hw.springboot.datasource;

import com.hw.springboot.DataSourceKeyConst;
import com.hw.springboot.DynamicDataSource;
import com.hw.springboot.DynamicDataSourceContextHolder;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huwei on 2017/7/6.
 */
public class MyDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private DataSource masterDataSource;
    private List<DataSource> slaveDataSources = new ArrayList<>();

    private ConversionService conversionService = new DefaultConversionService();
    private PropertyValues dataSourcePropertyValues;

    @Override
    public void setEnvironment(Environment environment) {

        // 读取主数据源
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.test.");

        masterDataSource = buildDataSource(propertyResolver);
        dataBinder(masterDataSource,environment);

        RelaxedPropertyResolver propertyResolver1 = new RelaxedPropertyResolver(environment, "spring.datasource.test1.");

        DataSource d1 = buildDataSource(propertyResolver1);
        slaveDataSources.add(d1);
        dataBinder(d1,environment);

        RelaxedPropertyResolver propertyResolver2 = new RelaxedPropertyResolver(environment, "spring.datasource.test2.");

        DataSource d2 = buildDataSource(propertyResolver2);
        slaveDataSources.add(d2);
        dataBinder(d2,environment);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyMasterSlaveDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("masterDataSource", masterDataSource);
        mpv.addPropertyValue("slaveDataSources", slaveDataSources);
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
    }
    /**
     * 创建DataSource
     *
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(RelaxedPropertyResolver propertyResolver) {
        try {
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("type", propertyResolver.getProperty("type"));
            dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
            dsMap.put("url", propertyResolver.getProperty("url"));
            dsMap.put("username", propertyResolver.getProperty("username"));
            dsMap.put("password", propertyResolver.getProperty("password"));

            Object type = dsMap.get("type");

            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为DataSource绑定更多数据
     *
     * @param dataSource
     * @param env
     * @author SHANHY
     * @create  2016年1月25日
     */
    private void dataBinder(DataSource dataSource, Environment env){
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        //dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
        dataBinder.setConversionService(conversionService);
        dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true
        if(dataSourcePropertyValues == null){
            Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
            Map<String, Object> values = new HashMap<>(rpr);
            // 排除已经设置的属性
            values.remove("type");
            values.remove("driver-class-name");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }
}
