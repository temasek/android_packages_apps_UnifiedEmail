/**
 * Copyright (c) 2011, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.mail.compose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.mail.R;
import com.android.mail.providers.Account;
import com.android.mail.providers.ReplyFromAccount;

import java.util.List;

/**
 * FromAddressSpinnerAdapter returns the correct spinner adapter for reply from
 * addresses based on device size.
 *
 * @author mindyp@google.com
 */
public class FromAddressSpinnerAdapter extends ArrayAdapter<ReplyFromAccount> {
    public static int REAL_ACCOUNT = 2;

    public static int ACCOUNT_DISPLAY = 0;

    public static int ACCOUNT_ADDRESS = 1;

    private LayoutInflater mInflater;

    public FromAddressSpinnerAdapter(Context context) {
        super(context, R.layout.from_item, R.id.spinner_account_name);
    }

    protected LayoutInflater getInflater() {
        if (mInflater == null) {
            mInflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReplyFromAccount fromItem = getItem(position);
        int res = fromItem.isCustomFrom ? R.layout.custom_from_item : R.layout.from_item;
        View fromEntry = getInflater().inflate(res, null);
        ((TextView) fromEntry.findViewById(R.id.spinner_account_name)).setText(fromItem.name);
        return fromEntry;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ReplyFromAccount fromItem = getItem(position);
        int res = fromItem.isCustomFrom ? R.layout.custom_from_dropdown_item
                : R.layout.from_dropdown_item;
        View fromEntry = getInflater().inflate(res, null);
        TextView acctName = ((TextView) fromEntry.findViewById(R.id.spinner_account_name));
        acctName.setText(fromItem.name);
        return fromEntry;
    }

    public int addAccounts(ReplyFromAccount selectedAccount,
            List<ReplyFromAccount> replyFromAccounts) {
        int currentIndex = 0;
        int currentAccountIndex = 0;
        // Get the position of the current account
        for (ReplyFromAccount account : replyFromAccounts) {
            // Add the account to the Adapter
            add(account);
            // See if we have located the selected account.
            if (selectedAccount.name.equals(account.name)) {
                currentAccountIndex = currentIndex;
            }

            currentIndex++;
        }
        return currentAccountIndex;
    }
}
