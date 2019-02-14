package com.mibs.asterisk.events;


import com.mibs.callboard.Wrapper;

public  interface AsteriskEvent {

	void execute(Wrapper wrapper);
}
