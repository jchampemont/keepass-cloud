Vue.component('crud-modal', {
    props: ["endpoint"],
    data: function () {
        return {
            active: false,
            id: "",
            editing: false,
            model: {
                name: ""
            }
        }
    },
    methods: {
        create: function() {
            axios
                .post('/api/' + this.endpoint, this.model)
                .then(response => {
                    this.$parent.add(response.data);
                    this.close();
                })
                .catch(error => {
                    this.$toast.open({
                        duration: 5000,
                        message: "Erreur: " + error.response.data.message,
                        queue: false,
                        position: 'is-top',
                        type: 'is-danger'
                    });
                });
        },
        update: function() {
            axios
                .put('/api/' + this.endpoint + '/' + this.id, this.model)
                .then(response => {
                    this.$parent.refresh();
                    this.close();
                })
                .catch(error => {
                    this.$toast.open({
                        duration: 5000,
                        message: "Erreur: " + error.response.data.message,
                        queue: false,
                        position: 'is-top',
                        type: 'is-danger'
                    });
                });
        },
        edit: function(id) {
            axios
                .get('/api/' + this.endpoint + '/' + id)
                .then(response => {
                   this.id = id;
                   this.model.name = response.data.name;
                   this.editing = true;
                   this.active = true;
                });
        },
        close: function() {
            this.active = false;
            this.editing = false;
            this.id = "";
            this.model.name = "";
        },
    },
    template: `
        <div>
            <button class="button is-primary is-medium"
                @click="active = true">
                Créer
            </button>
            <b-modal :active.sync="active" has-modal-card :canCancel="false">
                <form>
                    <div class="modal-card" style="width: auto">
                        <header class="modal-card-head">
                            <p class="modal-card-title" v-if="!editing">Créer une base de données de mots de passe.</p>
                            <p class="modal-card-title" v-if="editing">Modifier une base de données de mots de passe.</p>
                        </header>
                        <section class="modal-card-body">
                            <b-field label="ID" v-if="editing">
                                <b-input
                                    type="text"
                                    v-model="id"
                                    disabled>
                                </b-input>
                            </b-field>
                            <b-field label="Nom">
                                <b-input
                                    type="text"
                                    v-model="model.name"
                                    maxlength="64"
                                    placeholder="Nom de la base de données"
                                    required>
                                </b-input>
                            </b-field>
                        </section>
                        <footer class="modal-card-foot">
                            <button class="button" type="button" @click="close()">Fermer</button>
                            <button class="button is-primary" type="button" @click="create()" v-if="!editing">Créer</button>
                            <button class="button is-primary" type="button" @click="update()" v-if="editing">Modifier</button>
                        </footer>
                    </div>
                </form>
            </b-modal>
        </div>
    `
});