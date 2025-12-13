const API_URL = '/api/guests';

// Register / Edit Form Handler
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    const urlParams = new URLSearchParams(window.location.search);
    const guestId = urlParams.get('id');

    if (guestId) {
        document.querySelector('h2').textContent = 'Editar Hóspede';
        document.querySelector('button[type="submit"]').textContent = 'Salvar Alterações';
        loadGuestForEdit(guestId);
    }

    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const guest = {
            name: document.getElementById('name').value,
            cpf: stripNonDigits(document.getElementById('cpf').value),
            birthDate: document.getElementById('birthDate').value,
            phone: stripNonDigits(document.getElementById('phone').value),
            email: document.getElementById('email').value,
            addressStreet: document.getElementById('addressStreet').value,
            addressNumber: document.getElementById('addressNumber').value,
            addressZip: document.getElementById('addressZip').value,
            addressNeighborhood: document.getElementById('addressNeighborhood').value,
            addressComplement: document.getElementById('addressComplement').value,
            addressCity: document.getElementById('addressCity').value,
            addressState: document.getElementById('addressState').value
        };

        try {
            let response;
            if (guestId) {
                response = await fetch(`${API_URL}/${guestId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(guest)
                });
            } else {
                response = await fetch(API_URL, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(guest)
                });
            }

            if (response.ok) {
                alert('Salvo com sucesso!');
                window.location.href = 'guests.html';
            } else {
                const error = await response.text();
                alert('Erro: ' + error);
            }
        } catch (err) {
            alert('Erro de conexão: ' + err.message);
        }
    });
}

// Load Guests for List Page
async function loadGuests() {
    try {
        const response = await fetch(API_URL);
        const guests = await response.json();
        renderGuests(guests);
    } catch (err) {
        console.error('Error loading guests:', err);
    }
}

// Filter Guests
async function filterGuests() {
    const query = document.getElementById('filterInput').value;
    try {
        const response = await fetch(`${API_URL}?filter=${encodeURIComponent(query)}`);
        const guests = await response.json();
        renderGuests(guests);
    } catch (err) {
        console.error('Error filtering guests:', err);
    }
}

// Render Table
function renderGuests(guests) {
    const tbody = document.getElementById('guestsTableBody');
    tbody.innerHTML = '';

    guests.forEach(guest => {
        const tr = document.createElement('tr');
        const statusClass = guest.active ? 'status-active' : 'status-inactive';
        const statusText = guest.active ? 'Ativo' : 'Inativo';

        tr.innerHTML = `
            <td>${guest.name}</td>
            <td>${guest.cpf}</td>
            <td>${guest.phone}</td>
            <td>${guest.email}</td>
            <td>${formatAddress(guest)}</td>
            <td class="${statusClass}">${statusText}</td>
            <td>
                <button class="action-btn secondary" onclick="editGuest(${guest.id})">Editar</button>
                <button class="action-btn danger" onclick="deleteGuest(${guest.id})">Excluir</button>
                ${guest.active ? `<button class="action-btn warning" onclick="inactivateGuest(${guest.id})">Inativar</button>` : ''}
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function formatAddress(guest) {
    const parts = [];
    if (guest.addressStreet) parts.push(guest.addressStreet);
    if (guest.addressNumber) parts.push(guest.addressNumber);
    if (guest.addressNeighborhood) parts.push(guest.addressNeighborhood);
    if (guest.addressCity) parts.push(guest.addressCity);
    if (guest.addressState) parts.push(guest.addressState);
    return parts.join(', ') || '-';
}

// Inactivate Guest (Soft Delete)
async function inactivateGuest(id) {
    if (!confirm('Deseja realmente inativar este hóspede?')) return;

    try {
        const response = await fetch(`${API_URL}/${id}/inactivate`, { method: 'DELETE' });
        if (response.ok) {
            loadGuests();
        } else {
            alert('Erro ao inativar');
        }
    } catch (err) {
        alert('Erro de conexão: ' + err.message);
    }
}

// Delete Guest (Hard Delete)
async function deleteGuest(id) {
    if (!confirm('Deseja realmente EXCLUIR permanentemente este hóspede?')) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadGuests();
        } else {
            alert('Erro ao excluir');
        }
    } catch (err) {
        alert('Erro de conexão: ' + err.message);
    }
}

// Redirect to Edit
function editGuest(id) {
    window.location.href = `register.html?id=${id}`;
}

// Load Guest Details for Edit
async function loadGuestForEdit(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (response.ok) {
            const guest = await response.json();
            document.getElementById('name').value = guest.name;
            document.getElementById('cpf').value = guest.cpf;
            document.getElementById('birthDate').value = guest.birthDate;
            document.getElementById('phone').value = guest.phone;
            document.getElementById('email').value = guest.email;
            document.getElementById('addressStreet').value = guest.addressStreet || '';
            document.getElementById('addressNumber').value = guest.addressNumber || '';
            document.getElementById('addressZip').value = guest.addressZip || '';
            document.getElementById('addressNeighborhood').value = guest.addressNeighborhood || '';
            document.getElementById('addressComplement').value = guest.addressComplement || '';
            document.getElementById('addressCity').value = guest.addressCity || '';
            document.getElementById('addressState').value = guest.addressState || '';

            // Disable CPF as it is unique and usually shouldn't be changed easily or add logic to handle check
            // document.getElementById('cpf').readOnly = true; // Enabled for updates now
        } else {
            alert('Erro ao carregar dados do hóspede');
        }
    } catch (err) {
        console.error(err);
    }
}

// ==========================================
// INPUT MASKS
// ==========================================

document.addEventListener('DOMContentLoaded', () => {
    const cpfInput = document.getElementById('cpf');
    const phoneInput = document.getElementById('phone');
    const zipInput = document.getElementById('addressZip');

    if (cpfInput) {
        cpfInput.addEventListener('input', (e) => {
            e.target.value = applyCpfMask(e.target.value);
        });
    }

    if (phoneInput) {
        phoneInput.addEventListener('input', (e) => {
            e.target.value = applyPhoneMask(e.target.value);
        });
    }

    if (zipInput) {
        zipInput.addEventListener('input', (e) => {
            e.target.value = applyZipMask(e.target.value);
        });
    }
});

function applyZipMask(value) {
    value = value.replace(/\D/g, '');
    if (value.length > 8) value = value.slice(0, 8);
    return value.replace(/(\d{5})(\d)/, '$1-$2');
}

function applyCpfMask(value) {
    // Remove non-numeric
    value = value.replace(/\D/g, '');

    // Limit to 11 digits
    if (value.length > 11) value = value.slice(0, 11);

    // Apply mask ###.###.###-##
    return value
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d{1,2})/, '$1-$2')
        .replace(/(-\d{2})\d+?$/, '$1');
}

function applyPhoneMask(value) {
    // Remove non-numeric
    value = value.replace(/\D/g, '');

    // Limit to 11 digits
    if (value.length > 11) value = value.slice(0, 11);

    // Apply mask (##) #####-#### or (##) ####-####
    if (value.length > 10) {
        return value
            .replace(/^(\d\d)(\d{5})(\d{4}).*/, '($1) $2-$3');
    } else if (value.length > 5) {
        return value
            .replace(/^(\d\d)(\d{4})(\d{0,4}).*/, '($1) $2-$3');
    } else if (value.length > 2) {
        return value
            .replace(/^(\d\d)(\d{0,5})/, '($1) $2');
    } else {
        return value;
    }
}

function stripNonDigits(value) {
    return value.replace(/\D/g, '');
}
