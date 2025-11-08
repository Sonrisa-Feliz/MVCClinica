window.addEventListener('load', () => {
    const form = document.querySelector('#agregar_paciente_form');
    const modalEl = document.getElementById('staticBackdrop');
    const modal = modalEl ? new bootstrap.Modal(modalEl) : null;

    // Enganchar el botón "Save changes" del modal para que envíe el form
    const saveBtn = modalEl?.querySelector('.modal-footer .btn.btn-primary');
    if (saveBtn && form) {
        saveBtn.addEventListener('click', () => form.requestSubmit());
    }

    if (!form) return;

    function todayLocalYYYYMMDD() {
        const now = new Date();
        const tzOffset = now.getTimezoneOffset(); // en minutos
        const local = new Date(now.getTime() - tzOffset * 60000);
        return local.toISOString().slice(0, 10); // "YYYY-MM-DD"
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Armar payload (lo que tu backend espera para crear el paciente)
        const payload = {
            nombre: document.querySelector('#add_nombre')?.value.trim() || '',
            apellido: document.querySelector('#add_apellido')?.value.trim() || '',
            numeroContacto: document.querySelector('#add_numContacto')?.value.trim() || '',
            email: document.querySelector('#add_email')?.value.trim() || '',
            fechaIngreso: todayLocalYYYYMMDD(),   // <-- agregado acá
            domicilio: {
                calle: document.querySelector('#add_domi_calle')?.value.trim() || '',
                numero: Number(document.querySelector('#add_domi_numero')?.value.trim() || 0),
                localidad: document.querySelector('#add_domi_localidad')?.value.trim() || '',
                provincia: document.querySelector('#add_domi_provincia')?.value.trim() || ''
            }
            // fechaIngreso: la debería setear el backend; no la mandamos desde el form
        };

        try {
            const resp = await fetch('/paciente', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

            // Intentar leer el JSON creado (puede venir 201 con body)
            let data = null;
            try { data = await resp.json(); } catch (_) {}

            // Cerrar modal y limpiar formulario
            form.reset();
            if (modal) modal.hide();

            // Insertar fila en la tabla si vino el objeto creado; si no, refrescar
            if (data && data.id) {
                const table = document.getElementById('pacienteTable');
                if (table) {
                    const row = table.insertRow();
                    const tr_id = 'tr_' + data.id;
                    row.id = tr_id;

                    // Botones compatibles con tu listado/handlers existentes
                    const updateButton = '<button' +
                        ' id=' + '\"' + 'btn_id_' + data.id + '\"' +
                        ' type="button" onclick="findBy(' + data.id + ')" class="btn btn-primary btn_id">' +
                        '<i class=\"bi bi-eye-fill\"></i>' +
                        '</button>';

                    const deleteButton =
                        '<button id="btn_delete_' + data.id + '" type="button" ' +
                        'onclick="deleteBy(' + data.id + ')" class="btn btn-danger btn_delete">' +
                        '<i class="bi bi-trash"></i></button>';

                    row.innerHTML =
                        '<td class="td_id">' + data.id + '</td>' +
                        '<td class="td_nombre">' + (data.nombre ?? payload.nombre) + '</td>' +
                        '<td class="td_apellido">' + (data.apellido ?? payload.apellido) + '</td>' +
                        '<td class="td_contacto">' + (data.numeroContacto ?? payload.numeroContacto) + '</td>' +
                        '<td class="td_contacto">' + (data.fechaIngreso ?? '') + '</td>' +
                        '<td class="td_contacto">' + (data.email ?? payload.email) + '</td>' +
                        '<td>' + updateButton + '</td>' +
                        '<td>' + deleteButton + '</td>';
                } else {
                    window.location.reload();
                }
            } else {
                window.location.reload();
            }

            showToast('Paciente agregado', 'success');
        } catch (err) {
            console.error(err);
            showToast('No se pudo agregar el paciente. Intentá nuevamente.', 'danger');
        }
    });

    // Toast Bootstrap 5 (auto-contenido, sin tocar tu HTML)
    function showToast(message, type = 'success') {
        let container = document.getElementById('toastContainer');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toastContainer';
            container.className = 'toast-container position-fixed top-0 end-0 p-3';
            document.body.appendChild(container);
        }
        const toastEl = document.createElement('div');
        toastEl.className = `toast align-items-center text-bg-${type} border-0`;
        toastEl.setAttribute('role', 'alert');
        toastEl.setAttribute('aria-live', 'assertive');
        toastEl.setAttribute('aria-atomic', 'true');
        toastEl.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">${message}</div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto"
                data-bs-dismiss="toast" aria-label="Close"></button>
      </div>`;
        container.appendChild(toastEl);
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
        toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
    }
});
