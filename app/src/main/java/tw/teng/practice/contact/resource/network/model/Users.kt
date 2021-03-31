package tw.teng.practice.contact.resource.network.model

class Users {
    var contacts: List<Contacts>? = null

    class Contacts {
        var id = 0
        var name: String? = "null Contacts name"
        var username: String? = "null Contacts username"
        var email: String? = "null Contacts email"
        var pictureUrl: String? = "null Contacts pictureUrl"
        var company: Company? = null
    }

    class Company {
        var name: String? = "null name"
        var catchPhrase: String? = "null catchPhrase"
        var bs: String? = "null bs"
    }
}