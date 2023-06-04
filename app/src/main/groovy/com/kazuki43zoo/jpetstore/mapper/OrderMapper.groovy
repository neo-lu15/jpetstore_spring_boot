/*
 *    Copyright 2016-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.kazuki43zoo.jpetstore.mapper

import com.kazuki43zoo.jpetstore.domain.Order
import com.kazuki43zoo.jpetstore.domain.OrderLine
import org.apache.ibatis.annotations.CacheNamespace
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * @author Kazuki Shimizu
 */
@Mapper
@CacheNamespace
interface OrderMapper {

    @Select('''
        SELECT
            BILLADDR1 AS billAddress1,
            BILLADDR2 AS billAddress2,
            BILLCITY,
            BILLCOUNTRY,
            BILLSTATE,
            BILLTOFIRSTNAME,
            BILLTOLASTNAME,
            BILLZIP,
            SHIPADDR1 AS shipAddress1,
            SHIPADDR2 AS shipAddress2,
            SHIPCITY,
            SHIPCOUNTRY,
            SHIPSTATE,
            SHIPTOFIRSTNAME,
            SHIPTOLASTNAME,
            SHIPZIP,
            CARDTYPE,
            COURIER,
            CREDITCARD,
            EXPRDATE AS expiryDate,
            LOCALE,
            ORDERDATE,
            ORDERS.ORDERID,
            TOTALPRICE,
            USERID AS username,
            STATUS
        FROM
            orders,
            orderstatus
        WHERE
            orders.USERID = #{username}
            AND orders.ORDERID = orderstatus.ORDERID
        ORDER BY
            ORDERDATE
    ''')
    List<Order> getOrdersByUsername(String username)

    @Select('''
        SELECT
            BILLADDR1 AS billAddress1,
            BILLADDR2 AS billAddress2,
            BILLCITY,
            BILLCOUNTRY,
            BILLSTATE,
            BILLTOFIRSTNAME,
            BILLTOLASTNAME,
            BILLZIP,
            SHIPADDR1 AS shipAddress1,
            SHIPADDR2 AS shipAddress2,
            SHIPCITY,
            SHIPCOUNTRY,
            SHIPSTATE,
            SHIPTOFIRSTNAME,
            SHIPTOLASTNAME,
            SHIPZIP,
            CARDTYPE,
            COURIER,
            CREDITCARD,
            EXPRDATE AS expiryDate,
            LOCALE,
            ORDERDATE,
            ORDERS.ORDERID,
            TOTALPRICE,
            USERID AS username,
            STATUS
        FROM
            orders, orderstatus
        WHERE
            orders.ORDERID = #{orderId}
            AND orders.ORDERID = orderstatus.ORDERID
    ''')
    Order getOrder(int orderId)

    @Insert('''
        INSERT INTO orders (
            ORDERID,
            USERID,
            ORDERDATE,
            SHIPADDR1,
            SHIPADDR2,
            SHIPCITY,
            SHIPSTATE,
            SHIPZIP,
            SHIPCOUNTRY,
            BILLADDR1,
            BILLADDR2,
            BILLCITY,
            BILLSTATE,
            BILLZIP,
            BILLCOUNTRY,
            COURIER,
            TOTALPRICE,
            BILLTOFIRSTNAME,
            BILLTOLASTNAME,
            SHIPTOFIRSTNAME,
            SHIPTOLASTNAME,
            CREDITCARD,
            EXPRDATE,
            CARDTYPE,
            LOCALE
        )
        VALUES(
            #{orderId},
            #{username},
            #{orderDate},
            #{shipAddress1},
            #{shipAddress2},
            #{shipCity},
            #{shipState},
            #{shipZip},
            #{shipCountry},
            #{billAddress1},
            #{billAddress2},
            #{billCity},
            #{billState},
            #{billZip},
            #{billCountry},
            #{courier},
            #{totalPrice},
            #{billToFirstName},
            #{billToLastName},
            #{shipToFirstName},
            #{shipToLastName},
            #{creditCard},
            #{expiryDate},
            #{cardType},
            #{locale}
        )
    ''')
    void insertOrder(Order order)

    @Insert('''<script>
        <bind name="lineNum" value="_parameter.getLines().size()" />

        INSERT INTO orderstatus (
            ORDERID,
            LINENUM,
            TIMESTAMP,
            STATUS
        )
        VALUES (
            #{orderId},
            #{lineNum},
            #{orderDate},
            #{status}
        )
    </script>''')
    void insertOrderStatus(Order order)

    @Select('''
        SELECT
            ORDERID,
            LINENUM AS lineNumber,
            ITEMID,
            QUANTITY,
            UNITPRICE
        FROM
            orderlines
        WHERE
            ORDERID = #{orderId}
    ''')
    List<OrderLine> getOrderLines(int orderId)

    @Insert('''
        INSERT INTO orderlines (
            ORDERID,
            LINENUM,
            ITEMID,
            QUANTITY,
            UNITPRICE
        )
        VALUES (
            #{orderId},
            #{lineNumber},
            #{itemId},
            #{quantity},
            #{unitPrice}
        )
    ''')
    void insertOrderLine(OrderLine orderLine)


}
