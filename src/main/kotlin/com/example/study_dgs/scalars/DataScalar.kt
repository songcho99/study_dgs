package com.example.study_dgs.scalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

//@DgsScalar(name = "Date")
class DataScalar: Coercing<LocalDate, String> {

    // 이 함수는 서버에서 클라이언트로 데이터를 보낼 때 타입을 변환시켜준다.
    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        if(dataFetcherResult is LocalDate) {
            // DateTime 객체를 String 으로 변환
            return dataFetcherResult.format(DateTimeFormatter.ISO_LOCAL_DATE)
        } else {
            throw CoercingSerializeException("유효한 데이터가 아닙니다.")
        }
    }

    // 이 함수는 클라이언트에서 서버로 데이터를 보낼 때 쿼리에 직접 하드코딩으로 입력한 값을 변환할 수 있다.
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): LocalDate? {
        if(input is StringValue) {
            // String 객체를 DateTime 으로 변환
            return LocalDate.parse(input.value, DateTimeFormatter.ISO_LOCAL_DATE)
        } else {
            throw CoercingParseLiteralException("값이 유효한 ISO 날짜가 아닙니다.")
        }
    }

    // 이 함수는 클라이언트에서 서비로 보낼 떄 variables를 사용한 값들을 변환시켜준다
    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): LocalDate? {
        // String -> DateTime 으로 변환
        return LocalDate.parse(input.toString(), DateTimeFormatter.ISO_LOCAL_DATE)
    }
}