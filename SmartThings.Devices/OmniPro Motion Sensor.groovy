/**
 */
metadata {
	definition (name: "OmniPro Motion Sensor", namespace: "taasss", author: "taasss") {
		capability "Motion Sensor"
		capability "Sensor"

		command "active"
		command "inactive"
        command "toggle"
                
        attribute "type", "string"
        attribute "subType", "string"
        attribute "lastUpdateDate", "string"
        attribute "lastUpdateTime", "string"
	}

	tiles(scale: 2){
		standardTile("motion", "device.motion", width: 4, height: 4) {
			state("active", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#53a7c0")
			state("inactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff")
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
        
		main "motion"
		details(["motion", "subType", "lastUpdateTime"])
	}
}

def active() {
	log.info "${device.displayName}.active()"
	sendEvent(name: "motion", value: "active")
    
    // Mark in active in 5 seconds
    runIn(30, inactive, [overwrite: true])
}

def inactive() {
	log.info "${device.displayName}.inactive()"
    sendEvent(name: "motion", value: "inactive")
}

def toggle() {
	log.info "${device.displayName}.toggle()"
    
    if (device.latestValue("motion") == "inactive") {
    	active()
    }
    else {
    	inactive()
    }
}

def update(subType, status, originalJson) {
	log.info "${device.displayName}.update(${subType}, ${status}, ${originalJson})"

    sendEvent(name: "type", value: "Motion Sensor", displayed: false)
    sendEvent(name: "subType", value: subType, displayed: false)
    sendEvent(name: "lastUpdateDate", value: ((new Date()).format('yyyy-MM-dd', location.timeZone)), displayed: false)
    sendEvent(name: "lastUpdateTime", value:((new Date()).format('HH:mm:ss', location.timeZone)), displayed: false)

	if (status == "toggle") {
        toggle()
    }
    else if (status == "inactive") {
        inactive()        	
    }
    else if (status == "active") {
        active()        	
    }
}
