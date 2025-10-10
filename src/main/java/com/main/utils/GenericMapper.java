package com.main.utils;

import com.main.dto.UserDto;
import com.main.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

@Component
public class GenericMapper{
    private static final Logger logger = LoggerFactory.getLogger(GenericMapper.class);
    public <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) return null;
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Mapping failed for: " + source.getClass().getSimpleName(), e);
        }
    }


    public static void copyNonNullProperties(Object source, Object target) {
        BeanWrapper src = new BeanWrapperImpl(source);
        BeanWrapper trg = new BeanWrapperImpl(target);

        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            Object value = src.getPropertyValue(propertyName);

            if (value != null && trg.isWritableProperty(propertyName)) {
                Object oldValue = trg.getPropertyValue(propertyName);
                trg.setPropertyValue(propertyName, value);

                if (!value.equals(oldValue)) {
                    logger.info("Updated field: " + propertyName + " (old=" + oldValue + ", new=" + value + ")");
                }
            }
        }
    }


}
