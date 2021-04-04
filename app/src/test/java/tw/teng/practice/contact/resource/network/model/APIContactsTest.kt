package tw.teng.practice.contact.resource.network.model

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import tw.teng.practice.contact.resource.network.mock.Data

class APIContactsTest {

    @Test
    fun `test APIContact sync`() {
        val resource: APIContacts = Gson().fromJson(Data().mList, APIContacts::class.java)
        val expected = resource
        expected.contacts!![2].starred = true
        expected.contacts!![4].starred = true
        var actual = resource
        val starred = HashMap<Int, Boolean>()
        starred[2] = true
        starred[4] = true
        actual = resource.sync(resource, starred)
        Assert.assertTrue(theSameContacts(expected, actual))
    }

    private fun theSameContacts(
        contacts1: APIContacts,
        contacts2: APIContacts
    ): Boolean {
        for (i in 0 until contacts1.contacts!!.size) {
            if (contacts1.contacts!![i].starred != contacts2.contacts!![i].starred)
                return false
        }
        return true
    }
}