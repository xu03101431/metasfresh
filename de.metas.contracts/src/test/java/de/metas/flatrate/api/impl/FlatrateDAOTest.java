package de.metas.flatrate.api.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.process.DocAction;
import org.compiere.util.TimeUtil;
import org.junit.Test;

import de.metas.flatrate.ContractsTestBase;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Matching;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;

/*
 * #%L
 * de.metas.contracts
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

public class FlatrateDAOTest extends ContractsTestBase
{
	@Test
	public void test()
	{
		final Timestamp now = SystemTime.asTimestamp();

		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, getContext());
		InterfaceWrapperHelper.save(product);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, getContext());
		InterfaceWrapperHelper.save(bpartner);

		final I_C_Flatrate_Conditions fc = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		fc.setDocStatus(DocAction.STATUS_Completed);
		fc.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_QualityBasedInvoicing);
		InterfaceWrapperHelper.save(fc);

		final I_C_Flatrate_Matching matching = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Matching.class, getContext());
		matching.setC_Flatrate_Conditions(fc);
		matching.setM_Product(product);
		InterfaceWrapperHelper.save(matching);

		final I_C_Flatrate_Term ft = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		ft.setDocStatus(DocAction.STATUS_Completed);
		ft.setC_Flatrate_Conditions(fc);
		ft.setBill_BPartner(bpartner);
		ft.setStartDate(TimeUtil.addDays(now, -10));
		ft.setEndDate(TimeUtil.addDays(now, 10));
		InterfaceWrapperHelper.save(ft);

		final List<I_C_Flatrate_Term> result = new FlatrateDAO().retrieveTerms(
				getContext().getCtx(),
				bpartner.getC_BPartner_ID(),
				now,
				0,
				product.getM_Product_ID(),
				0,
				getContext().getTrxName());

		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(ft));
	}
}
