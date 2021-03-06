package de.metas.edi.esb.commons;

/*
 * #%L
 * de.metas.edi.esb
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static de.metas.edi.esb.commons.Util.isEmpty;

import java.util.List;

import org.apache.camel.RuntimeCamelException;

/**
 * Helper class, used in field validation. Methods here should throw unchecked exceptions (preferably which extend {@link RuntimeCamelException}).
 * 
 * @author al
 */
public final class ValidationHelper
{
	private ValidationHelper()
	{
		super();
	}

	/**
	 * Validate any type, and throw {@link RuntimeCamelException} if the field is null.
	 * 
	 * @param field
	 * @param errorMsg
	 */
	public static void validateObject(final Object field, final String errorMsg)
	{
		if (field == null)
		{
			throw new RuntimeCamelException(errorMsg);
		}
	}

	/**
	 * Validate {@link String}, and throw {@link RuntimeCamelException} if the field is null or empty.
	 * 
	 * @param field
	 * @param errorMsg
	 */
	public static void validateString(final String field, final String errorMsg)
	{
		if (isEmpty(field))
		{
			throw new RuntimeCamelException(errorMsg);
		}
	}

	/**
	 * Validate {@link Number}, and throw {@link RuntimeCamelException} if the field is null or 0.
	 * 
	 * @param field
	 * @param errorMsg
	 */
	public static void validateNumber(final Number field, final String errorMsg)
	{
		if (field == null || field.intValue() == 0)
		{
			throw new RuntimeCamelException(errorMsg);
		}
	}

	public static void validateList(final List<?> field, final String errorMsg)
	{
		if (field == null || field.isEmpty())
		{
			throw new RuntimeCamelException(errorMsg);
		}
	}
}
