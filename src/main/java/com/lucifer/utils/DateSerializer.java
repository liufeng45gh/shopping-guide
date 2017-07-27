package com.lucifer.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 更改查询到的Date 数据以字符串形式
 * 
 * @author zxw
 *
 */
public class DateSerializer extends JsonSerializer<Date> {

	private static final long ONE_MINUTE = 60;
	private static final long ONE_HOUR = 3600;
	private static final long ONE_DAY = 86400;
	// private static final long ONE_MONTH = 2592000;
	// private static final long ONE_YEAR = 31104000;

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formatDate = format.format(value);
		jgen.writeString(formatDate);

	}


}
