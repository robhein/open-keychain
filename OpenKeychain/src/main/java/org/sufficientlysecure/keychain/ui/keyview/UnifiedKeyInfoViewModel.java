package org.sufficientlysecure.keychain.ui.keyview;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import org.sufficientlysecure.keychain.livedata.GenericLiveData;
import org.sufficientlysecure.keychain.model.SubKey.UnifiedKeyInfo;
import org.sufficientlysecure.keychain.provider.KeyRepository;
import org.sufficientlysecure.keychain.provider.KeychainContract.KeyRings;


public class UnifiedKeyInfoViewModel extends ViewModel {
    private Long masterKeyId;
    private LiveData<UnifiedKeyInfo> unifiedKeyInfoLiveData;

    public void setMasterKeyId(long masterKeyId) {
        if (this.masterKeyId != null) {
            throw new IllegalStateException("cannot change masterKeyId once set!");
        }
        this.masterKeyId = masterKeyId;
    }

    public long getMasterKeyId() {
        return masterKeyId;
    }

    public LiveData<UnifiedKeyInfo> getUnifiedKeyInfoLiveData(Context context) {
        if (masterKeyId == null) {
            throw new IllegalStateException("masterKeyId must be set to retrieve this!");
        }
        if (unifiedKeyInfoLiveData == null) {
            KeyRepository keyRepository = KeyRepository.create(context);
            unifiedKeyInfoLiveData = new GenericLiveData<>(context, KeyRings.buildGenericKeyRingUri(masterKeyId),
                    () -> keyRepository.getUnifiedKeyInfo(masterKeyId));
        }
        return unifiedKeyInfoLiveData;
    }
}
