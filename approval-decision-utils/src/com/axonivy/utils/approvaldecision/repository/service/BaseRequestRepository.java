package com.axonivy.utils.approvaldecision.repository.service;

import com.axonivy.utils.approvaldecision.repository.bean.BaseRequest;

import ch.ivyteam.ivy.environment.Ivy;

public abstract class BaseRequestRepository<R extends BaseRequest<?>> extends BaseRepository<R> {

	public R findByCaseId(Long caseId) {
		R request = Ivy.repo().search(getType())
				.numberField("caseId").isEqualTo(caseId)
				.execute().getFirst();
		return request;
	}

	@Override
	public R save(R obj) {
		obj = super.save(obj);
		Long caseId = obj.getCaseId();
		
		int maxRetries = 10;
		int delayMs = 200;

		for (int i = 0; i < maxRetries; i++) {
		var result = findByCaseId(caseId);
		    if (result != null) {
		        return result;
		    }
		    try {
				Thread.sleep(delayMs * (i+1));
			} catch (InterruptedException e) {
				Ivy.log().error("Error when verify after saving BaseRequest", e);
			}
		}
		Ivy.log().error("BaseRequest not found after saving.");
		return obj;
	}
}
