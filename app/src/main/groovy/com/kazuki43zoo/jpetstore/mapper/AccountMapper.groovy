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

import com.kazuki43zoo.jpetstore.domain.Account
import org.apache.ibatis.annotations.CacheNamespace
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

/**
 * @author Kazuki Shimizu
 */
@Mapper
@CacheNamespace
interface AccountMapper {

    @Select('''
        SELECT
            signon.USERNAME,
            signon.PASSWORD,
            account.EMAIL,
            account.FIRSTNAME,
            account.LASTNAME,
            account.STATUS,
            account.ADDR1 AS address1,
            account.ADDR2 AS address2,
            account.CITY,
            account.STATE,
            account.ZIP,
            account.COUNTRY,
            account.PHONE,
            profile.LANGPREF AS languagePreference,
            profile.FAVCATEGORY AS favouriteCategoryId,
            profile.MYLISTOPT AS listOption,
            profile.BANNEROPT AS bannerOption,
            bannerdata.BANNERNAME
        FROM
            account, profile, signon, bannerdata
        WHERE
            account.USERID = #{username}
            AND signon.USERNAME = account.USERID
            AND profile.USERID = account.USERID
            AND profile.FAVCATEGORY = bannerdata.FAVCATEGORY
    ''')
    Account getAccountByUsername(String username)

    @Insert('''
        INSERT INTO account (
            EMAIL,
            FIRSTNAME,
            LASTNAME,
            STATUS,
            ADDR1,
            ADDR2,
            CITY,
            STATE,
            ZIP,
            COUNTRY,
            PHONE,
            USERID
        )
        VALUES (
            #{email},
            #{firstName},
            #{lastName},
            #{status},
            #{address1},
            #{address2},
            #{city},
            #{state},
            #{zip},
            #{country},
            #{phone},
            #{username}
        )
    ''')
    void insertAccount(Account account)

    @Insert('''
        INSERT INTO profile (
            LANGPREF,
            FAVCATEGORY,
            MYLISTOPT,
            BANNEROPT,
            USERID
        )
        VALUES (
            #{languagePreference},
            #{favouriteCategoryId},
            #{listOption},
            #{bannerOption},
            #{username}
        )
    ''')
    void insertProfile(Account account)

    @Insert('''
        INSERT INTO signon (
            PASSWORD,
            USERNAME
        )
        VALUES (
            #{password},
            #{username}
        )
    ''')
    void insertSignon(Account account)

    @Update('''
        UPDATE account SET
            EMAIL = #{email},
            FIRSTNAME = #{firstName},
            LASTNAME = #{lastName},
            STATUS = #{status},
            ADDR1 = #{address1},
            ADDR2 = #{address2},
            CITY = #{city},
            STATE = #{state},
            ZIP = #{zip},
            COUNTRY = #{country},
            PHONE = #{phone}
        WHERE
            USERID = #{username}
    ''')
    void updateAccount(Account account)

    @Update('''
        UPDATE profile SET
            LANGPREF = #{languagePreference},
            FAVCATEGORY = #{favouriteCategoryId},
            MYLISTOPT = #{listOption},
            BANNEROPT = #{bannerOption}
        WHERE
            USERID = #{username}
    ''')
    void updateProfile(Account account)

    @Update('''
        UPDATE signon SET
            PASSWORD = #{password}
        WHERE
            USERNAME = #{username}
    ''')
    void updateSignon(Account account)

}
