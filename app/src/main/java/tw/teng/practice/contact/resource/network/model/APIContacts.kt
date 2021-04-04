package tw.teng.practice.contact.resource.network.model

class APIContacts {
    var contacts: MutableList<Contacts>? = null

    open class Contacts {
        var starred: Boolean = false
        var id = 0
        var name: String? = "null Contacts name"
        var username: String? = "null Contacts username"
        var email: String? = "null Contacts email"
        var pictureUrl: String? = "null Contacts pictureUrl"
        var company: Company? = Company()

        class Company {
            var name: String? = "null name"
            var catchPhrase: String? = "null catchPhrase"
            var bs: String? = "null bs"

            fun copy(resource: Company) {
                this.name = resource.name
                this.catchPhrase = resource.catchPhrase
                this.bs = resource.bs
            }
        }

        fun copy(resource: Contacts) {
            this.name = resource.name
            this.username = resource.username
            this.email = resource.email
            this.pictureUrl = resource.pictureUrl
            resource.company?.let { this.company?.copy(it) }
        }
    }

    fun sync(resource: APIContacts): APIContacts {
        if (this.contacts == null) {
            for (temp: Contacts in resource.contacts!!) {
                this.contacts = resource.contacts
            }
        } else {
            for (temp: Contacts in resource.contacts!!) {
                var finded = false
                for (local: Contacts in this.contacts!!) {
                    if (local.id == temp.id) {
                        local.copy(temp)
                        finded = true
                        break
                    }
                }
                if (!finded) {
                    contacts!!.add(temp)
                }
            }
        }
        return this
    }
}