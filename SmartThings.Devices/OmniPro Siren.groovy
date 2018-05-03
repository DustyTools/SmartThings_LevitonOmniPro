/**
 *  Omni Pro Bridge - Siren
 */
metadata {
	definition (name: "OmniPro Siren", namespace: "taasss", author: "taasss") {
		capability "Alarm"
        
        command "both"
        command "siren"
        command "strobe"
        command "off"
        command "toggle"
                
        attribute "type", "string"
		attribute "subType", "string"
        attribute "lastUpdateDate", "string"
        attribute "lastUpdateTime", "string"
	}

	tiles(scale: 2){
		standardTile("contact", "device.contact", width: 4, height: 4) {
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#79b821")
			state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#ffa81e")
		}
        standardTile("subType", "device.subType", width: 2, height: 2) {
            state "Perimeter", label:'Perimeter', icon: "st.Home.home3", backgroundColor: "#ffffff"
            state "Outside", label:'Outside', icon: "st.Outdoor.outdoor22", backgroundColor: "#ffffff"
            state "Internal", icon: "st.Home.home4", backgroundColor: "#ffffff"
        }
        valueTile("lastUpdateDate", "device.lastUpdateDate", width: 2, height: 1) {
            state "lastUpdateDate", label: '${currentValue}', backgroundColor: "#ffffff"
        }
        valueTile("lastUpdateTime", "device.lastUpdateTime", width: 2, height: 2) {
            state "lastUpdateTime", label: '${currentValue}', backgroundColor: "#ffffff"
        }
        
		main "contact"
		details(["contact", "subType", "lastUpdateTime"])
	}
}

def strobe() {
	log.info "${device.displayName}.strobe()"
	sendEvent(name: "alarm", value: "siren")
}

def siren() {
	log.info "${device.displayName}.siren()"
	sendEvent(name: "alarm", value: "siren")
}

def both() {
	log.info "${device.displayName}.both()"
	sendEvent(name: "alarm", value: "siren")
}

def off() {
	log.info "${device.displayName}.off()"
    sendEvent(name: "alarm", value: "off")
}

def toggle() {
	log.info "${device.displayName}.toggle()"
    
    if (device.latestValue("alarm") == "off") {
    	siren()
    }
    else {
    	off()
    }
}

def update(subType, status, originalJson) {
	log.info "${device.displayName}.update(${subType}, ${status}, ${originalJson})"

    sendEvent(name: "type", value: "Siren", displayed: false)
    sendEvent(name: "subType", value: subType, displayed: false)
    sendEvent(name: "lastUpdateDate", value: ((new Date()).format('yyyy-MM-dd', location.timeZone)), displayed: false)
    sendEvent(name: "lastUpdateTime", value:((new Date()).format('HH:mm:ss', location.timeZone)), displayed: false)

	if (status == "toggle") {
        toggle()
    }
    else if (status == "siren") {
        siren()        	
    }
    else if (status == "off") {
        off()        	
    }
}
