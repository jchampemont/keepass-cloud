Vue.component('crud-modal', {
    props: ["endpoint"],
    data: function () {
        return {
            active: false,
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
                    this.model.name = "";
                    this.active = false;
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
        }
    },
    template: `
        <div>
            <button class="button is-primary is-medium"
                @click="active = true">
                Créer
            </button>
            <b-modal :active.sync="active" has-modal-card>
                <form>
                    <div class="modal-card" style="width: auto">
                        <header class="modal-card-head">
                            <p class="modal-card-title">Créer une base de données de mots de passe.</p>
                        </header>
                        <section class="modal-card-body">
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
                            <button class="button" type="button" @click="active = false">Fermer</button>
                            <button class="button is-primary" type="button" @click="create()">Créer</button>
                        </footer>
                    </div>
                </form>
            </b-modal>
        </div>
    `
});