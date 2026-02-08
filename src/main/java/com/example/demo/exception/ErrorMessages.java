package com.example.demo.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

import static com.example.demo.exception.ErrorMessages.ErrorCode.INVALID_UNIT_FOR_NOMENCLATURE;

public class ErrorMessages {

    private static final Logger log = LoggerFactory.getLogger(ErrorMessages.class);

    public static final Map<ErrorCode, String> exceptions = new EnumMap<>(ErrorCode.class);

    public enum ErrorCode {
        REQUISITION_NOT_FOUND,
        ITEM_NOT_FOUND,
        REQUISITION_NOT_EDITABLE,
        INVALID_PRICE,
        INVALID_QUANTITY,
        INVALID_ARGUMENT,
        OPTIMISTIC_LOCK,
        INVALID_DELIVERY_DATE,
        NOMENCLATURE_NOT_FOUND,
        INVALID_UNIT_FOR_NOMENCLATURE,
        DUPLICATE_NOMENCLATURE_IN_REQUISITION,
        REQUISITION_ITEM_VERSION_CONFLICT,
        ITEM_NOT_BELONG_TO_REQUISITION,
        LAST_ITEM_DELETE_FORBIDDEN,
        DELIVERY_DATE_MIN_NOT_FOUND,
        DELIVERY_DATE_MAX_NOT_FOUND,
        QUANTITY_INCORRECT
    }

    static {
        exceptions.put(ErrorCode.REQUISITION_NOT_FOUND,
                "Не найдена заявка");
        exceptions.put(ErrorCode.ITEM_NOT_FOUND,
                "Не найдена позиция в заявке");
        exceptions.put(ErrorCode.REQUISITION_NOT_EDITABLE,
                "Можно редактировать заявку только в статусе DRAFT");
        exceptions.put(ErrorCode.INVALID_PRICE,
                "Цена должна быть больше либо равна 0");
        exceptions.put(ErrorCode.INVALID_QUANTITY,
                "Количество должно быть больше 0");
        exceptions.put(ErrorCode.OPTIMISTIC_LOCK,
                "Данные были изменены другим пользователем");
        exceptions.put(ErrorCode.INVALID_DELIVERY_DATE,
                "desiredDeliveryDate не раньше чем текущая дата + 3 дня");
        exceptions.put(ErrorCode.NOMENCLATURE_NOT_FOUND,
                "Нет номенклатуры");

        exceptions.put(INVALID_UNIT_FOR_NOMENCLATURE,
                "Недопустимая единица измерения для номенклатуры");

        exceptions.put(ErrorCode.DUPLICATE_NOMENCLATURE_IN_REQUISITION,
                "В заявке уже есть позиция с таким кодом номенклатуры");

        exceptions.put(ErrorCode.REQUISITION_ITEM_VERSION_CONFLICT,
                "Версия позиции устарела");

        exceptions.put(ErrorCode.ITEM_NOT_BELONG_TO_REQUISITION,
                "Позиция не принадлежит заявке");

        exceptions.put(ErrorCode.LAST_ITEM_DELETE_FORBIDDEN,
                "Нельзя удалить последнюю позицию в заявке");

        exceptions.put(ErrorCode.DELIVERY_DATE_MIN_NOT_FOUND,
                "Не удалось определить минимальный срок доставки");

        exceptions.put(ErrorCode.DELIVERY_DATE_MAX_NOT_FOUND,
                "Не удалось определить максимальный срок доставки");

        exceptions.put(ErrorCode.QUANTITY_INCORRECT,
                "Количество должно быть не меньше 1");

    }

    public static String message(ErrorCode code) {
        return exceptions.get(code);
    }

}

